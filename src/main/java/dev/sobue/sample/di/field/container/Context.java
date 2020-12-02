package dev.sobue.sample.di.field.container;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 * Dependency Injection Container Implementation.
 *
 * @author Sho Sobue
 */
@Slf4j
public abstract class Context {

  /**
   * Container Object.
   */
  private static final Map<String, Object> container = new ConcurrentHashMap<>();

  // for trace log
  static {
    log.trace("container:{}", container);
  }

  /**
   * regist container by id.
   *
   * @param id name
   * @param object instance object
   */
  public static void register(final String id, final Object object) {
    container.put(id, object);
    log.trace("container:{}", container);
  }

  /**
   * initialize container: scan {@link Named} annotations and inject object to annotated {@link
   * Inject}.
   *
   * @param basePackages scan packages
   * @throws ClassNotFoundException unknown class specified
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   */
  public static void initialize(final String... basePackages)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException,
      InvocationTargetException {
    log.info("start scan objects of base packages");
    createInstances(basePackages);
    log.info("start injection to instance parameters");
    injection();
    log.trace("container:{}", container);
  }

  /**
   * create instance that presented {@link Named} annotation.
   *
   * @param basePackages scan packages
   * @throws ClassNotFoundException unknown class specified
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   */
  private static void createInstances(final String... basePackages)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException,
      InvocationTargetException {
    // Scan Implementation Classes
    log.debug("scan => basePackages:{}", Arrays.asList(basePackages));
    for (String packageName : basePackages) {
      log.debug("scan => basePackage:{}", packageName);

      // Get Class Name List
      List<String> classes = getClassNames(packageName);
      log.debug("class list => basePackage:{} classes:{}", packageName, classes);

      // Load Named Annotated Classes
      for (String className : classes) {
        Class<?> clazz = Class.forName(className);
        Annotation annotation = clazz.getAnnotation(Named.class);
        if (annotation != null) {
          log.debug("class:{} is Named annotation presented, register to container", className);
          if (Stream.of(clazz.getDeclaredConstructors())
              .noneMatch(c -> c.getParameterCount() == 0)) {
            throw new InstantiationException("no default constructor");
          }
          container.put(
              clazz.getSimpleName(),
              Stream.of(clazz.getDeclaredConstructors())
                  .filter(c -> c.getParameterCount() == 0)
                  .findFirst()
                  .get()
                  .newInstance());
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
  private static List<String> getClassNames(final String packageName) {
    String resourceName = packageName.replace('.', '/');
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL root = classLoader.getResource(resourceName);
    File rootFile = new File(Objects.requireNonNull(root).getFile());

    // Sub Packages
    File[] dirs = rootFile.listFiles((dir, name) -> !name.endsWith(".class"));
    // Classes in current package
    File[] files = rootFile.listFiles((dir, name) -> name.endsWith(".class"));

    // class list
    List<String> classes = new ArrayList<>();

    if (Objects.nonNull(dirs) && dirs.length > 0) {
      // Collect Class Names in Sub Packages
      Stream.of(dirs)
          .map(File::getName)
          .map(d -> getClassNames(packageName + "." + d))
          .forEach(classes::addAll);
      log.debug("class list in sub package => basePackage:{} classes:{}", packageName, classes);
    }

    if (Objects.nonNull(files) && files.length > 0) {
      // Collect Class Names in Current Packages
      classes.addAll(
          Stream.of(files)
              .map(File::getName)
              .map(name -> name.replaceAll(".class$", ""))
              .map(name -> packageName + "." + name)
              .collect(Collectors.toList()));
    }

    return classes;
  }

  /**
   * inject object to annotated {@link Inject} fields in class.
   */
  private static void injection() {
    container.forEach(
        (k, v) -> {
          Class<?> clazz = v.getClass();
          log.debug("start injection => class:{}", clazz.getName());

          for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
              log.debug(
                  "Inject annotation is not presented. class:{} field:{}",
                  clazz.getName(),
                  field.getName());
              continue;
            }

            boolean modifyAccessible = false;
            if (!field.trySetAccessible()) {
              modifyAccessible = true;
              field.setAccessible(true);
            }

            log.debug(
                "Inject annotation is presented. class:{} field:{}",
                clazz.getName(),
                field.getName());
            log.debug("start search object from container by class type");
            try {
              field.set(v, getBean(field.getType()));
            } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
            }

            if (modifyAccessible) {
              field.setAccessible(false);
            }
          }
        });
  }

  /**
   * get instance from container.
   *
   * @param targetClass class type
   * @param <T> interface class
   * @return instance
   */
  public static <T> T getBean(final Class<T> targetClass) {
    log.debug("search for object for class:{}", targetClass.getName());

    List<Object> result = new ArrayList<>();

    container.forEach(
        (k, v) -> {
          Class<?> entryClass = v.getClass();

          List<Class<?>> classes = new ArrayList<>();
          classes.add(entryClass);
          classes.addAll(getSuperClasses(new ArrayList<>(), entryClass));
          classes.addAll(Arrays.asList(entryClass.getInterfaces()));

          log.debug("target and super classes, interface classes. => classes:{}", classes);

          for (Class<?> searchClass : classes) {
            if (targetClass.equals(searchClass)) {
              result.add(v);
              log.debug("match container object.");
              break;
            }
          }
        });

    if (result.isEmpty()) {
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
