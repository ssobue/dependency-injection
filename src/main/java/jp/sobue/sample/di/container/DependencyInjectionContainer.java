package jp.sobue.sample.di.container;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import jp.sobue.sample.di.annotations.Implementation;
import jp.sobue.sample.di.annotations.InjectObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dependency Injection Container Implementation.
 *
 * @author Sho Sobue
 */
public class DependencyInjectionContainer {

  /** Logger. */
  private static final Logger logger = LoggerFactory.getLogger(DependencyInjectionContainer.class);

  /** Container Object. */
  private static final Map<String, Object> container = new HashMap<>();

  // for trace log
  static {
    logger.trace("container:{}", container);
  }

  /**
   * regist container by id.
   *
   * @param id name
   * @param object instance object
   */
  public static void registObject(final String id, final Object object) {
    container.put(id, object);
    logger.trace("container:{}", container);
  }

  /**
   * initialize container: scan component annotation, inject values.
   *
   * @param basePackages scan packages
   * @throws ClassNotFoundException unknown class specified
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   */
  public static void init(final String... basePackages)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    logger.info("start scan component objects of base packages");
    load(basePackages);
    logger.info("start injection object parameters");
    injection();
    logger.trace("container:{}", container);
  }

  /**
   * load classes and create instance that presented component annotation.
   *
   * @param basePackages scan packages
   * @throws ClassNotFoundException unknown class specified
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   */
  private static void load(final String... basePackages)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    // Scan Implementation Classes
    logger.debug("scan => basePackages:{}", Arrays.asList(basePackages));
    for (String packageName : basePackages) {
      logger.debug("scan => basePackage:{}", packageName);

      // Get Class Name List
      List<String> classes = getClassNameList(packageName);
      logger.debug("class list => basePackage:{} classes:{}", packageName, classes);

      // Load Implementation Annotated Classes
      for (String className : classes) {
        Class<?> clazz = Class.forName(className);
        Annotation annotation = clazz.getAnnotation(Implementation.class);
        if (annotation != null) {
          logger.debug(
              "class:{} is Implementation annotation presented, regist to container", className);
          container.put(clazz.getSimpleName(), clazz.newInstance());
        }
      }
    }
  }

  /**
   * get class name list by package name.
   *
   * @param packageName package name
   * @return list of class name
   */
  private static List<String> getClassNameList(final String packageName) {
    String resourceName = packageName.replace('.', '/');
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL root = classLoader.getResource(resourceName);

    File[] files =
        new File(Objects.requireNonNull(root).getFile())
            .listFiles((dir, name) -> name.endsWith(".class"));

    return Arrays.stream(Objects.requireNonNull(files))
        .parallel()
        .map(File::getName)
        .map(name -> name.replaceAll(".class$", ""))
        .map(name -> packageName + "." + name)
        .collect(Collectors.toList());
  }

  /**
   * inject values of container that presented autowired annotation.
   *
   * @throws IllegalAccessException not accessible class
   */
  private static void injection() throws IllegalAccessException {
    for (Entry<String, Object> object : container.entrySet()) {
      Class<?> clazz = object.getValue().getClass();
      logger.debug("start injection => class:{}", clazz.getName());

      for (Field field : clazz.getDeclaredFields()) {
        if (!field.isAnnotationPresent(InjectObject.class)) {
          logger.debug(
              "InjectObject is not presented. class:{} field:{}", clazz.getName(), field.getName());
          continue;
        }

        boolean modifyAccessible = false;
        if (!field.isAccessible()) {
          modifyAccessible = true;
          field.setAccessible(true);
        }

        logger.debug(
            "InjectObject is presented. class:{} field:{}", clazz.getName(), field.getName());
        logger.debug("start search object from container by class type");
        field.set(object.getValue(), getImplementation(field.getType()));

        if (modifyAccessible) {
          field.setAccessible(false);
        }
      }
    }
  }

  /**
   * get instance from container.
   *
   * @param targetClass class type
   * @param <T> interface class
   * @return instance
   */
  public static <T> T getImplementation(final Class<T> targetClass) {
    logger.debug("search for object for class:{}", targetClass.getName());

    List<Object> result = new ArrayList<>();
    for (Entry<String, Object> object : container.entrySet()) {
      Class<?> entryClass = object.getValue().getClass();

      List<Class<?>> classes = new ArrayList<>();
      classes.add(entryClass);
      classes.addAll(getSuperClasses(new ArrayList<>(), entryClass));
      classes.addAll(Arrays.asList(entryClass.getInterfaces()));

      logger.debug("target and super classes, interface classes. => classes:{}", classes);

      for (Class<?> searchClass : classes) {
        if (entryClass.equals(searchClass)) {
          result.add(object.getValue());
          logger.debug("match container object.");
          break;
        }
      }
    }

    if (result.size() == 0) {
      throw new IllegalStateException("no object");
    }

    if (result.size() > 1) {
      throw new IllegalStateException("not single object");
    }

    return targetClass.cast(result.get(0));
  }

  /**
   * get list of super class.
   *
   * @param superClasses list of super class
   * @param clazz target class
   * @return list of super class
   */
  private static List<Class<?>> getSuperClasses(
      final List<Class<?>> superClasses, final Class<?> clazz) {
    // continue until target class is Object
    if (!Object.class.equals(clazz)) {
      Class<?> superClass = clazz.getSuperclass();
      superClasses.add(superClass);
      getSuperClasses(superClasses, superClass);
    }
    return superClasses;
  }
}
