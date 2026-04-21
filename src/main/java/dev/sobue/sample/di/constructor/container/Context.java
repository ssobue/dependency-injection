package dev.sobue.sample.di.constructor.container;

import dev.sobue.sample.di.common.ClassPathClassNameCollector;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Dependency Injection Container Implementation (Constructor Injection).
 *
 * @author Sho Sobue
 */
@Slf4j
public final class Context {
  /**
   * Prevent instantiation.
   */
  private Context() {
    throw new AssertionError("no instances");
  }

  /**
   * Container Object.
   */
  private static final Map<Class<?>, Object> container = new ConcurrentHashMap<>();

  /**
   * Named classes for constructor injection.
   */
  private static final List<Class<?>> namedClasses = new ArrayList<>();

  // for trace log
  static {
    log.trace("container:{}", container);
  }

  /**
   * initialize container: scan {@link Named} annotations and inject object to constructor
   * parameters annotated by {@link Inject}.
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
    scanNamedClasses(basePackages);
    log.info("start constructor injection to instance parameters");
    createInstances();
    log.trace("container:{}", container);
  }

  /**
   * scan classes that presented {@link Named} annotation.
   *
   * @param basePackages scan packages
   * @throws ClassNotFoundException unknown class specified
   */
  private static void scanNamedClasses(final String... basePackages)
      throws ClassNotFoundException {
    // Scan Implementation Classes
    log.debug("scan => basePackages:{}", Arrays.asList(basePackages));
    for (String packageName : basePackages) {
      log.debug("scan => basePackage:{}", packageName);

      // Get Class Name List
      List<String> classes = ClassPathClassNameCollector.collect(packageName);
      log.debug("class list => basePackage:{} classes:{}", packageName, classes);

      // Load Named Annotated Classes
      for (String className : classes) {
        Class<?> clazz = Class.forName(className);
        if (clazz.isAnnotationPresent(Named.class)) {
          log.debug("class:{} is Named annotation presented, register for container", className);
          namedClasses.add(clazz);
        }
      }
    }
  }

  /**
   * create instances that presented {@link Named} annotation.
   *
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   */
  private static void createInstances()
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    for (Class<?> clazz : namedClasses) {
      getOrCreate(clazz);
    }
  }

  /**
   * get or create instance by class.
   *
   * @param clazz class
   * @return instance
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   * @throws InvocationTargetException constructor throws exception
   */
  private static Object getOrCreate(final Class<?> clazz)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    Object existing = container.get(clazz);
    if (existing != null) {
      return existing;
    }

    Object created = createInstance(clazz);
    Object raced = container.putIfAbsent(clazz, created);
    return raced != null ? raced : created;
  }

  /**
   * create instance from class with constructor injection.
   *
   * @param clazz target class
   * @return instance
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   * @throws InvocationTargetException constructor throws exception
   */
  private static Object createInstance(final Class<?> clazz)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    Constructor<?> constructor = selectConstructor(clazz);
    Object[] args = resolveConstructorArgs(constructor.getParameterTypes());

    if (!constructor.canAccess(null) && !constructor.trySetAccessible()) {
      throw new IllegalStateException("constructor is not accessible: " + clazz.getName());
    }

    return constructor.newInstance(args);
  }

  /**
   * select constructor for injection.
   *
   * @param clazz target class
   * @return constructor
   * @throws InstantiationException no constructor to use
   */
  private static Constructor<?> selectConstructor(final Class<?> clazz)
      throws InstantiationException {
    Constructor<?> injectConstructor = null;
    for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
      if (!constructor.isAnnotationPresent(Inject.class)) {
        continue;
      }
      if (injectConstructor != null) {
        throw new InstantiationException("multiple @Inject constructors");
      }
      injectConstructor = constructor;
    }

    if (injectConstructor != null) {
      return injectConstructor;
    }

    try {
      return clazz.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
      InstantiationException exception = new InstantiationException("no default constructor");
      exception.initCause(e);
      throw exception;
    }
  }

  /**
   * resolve constructor arguments by type.
   *
   * @param parameterTypes constructor parameter types
   * @return arguments
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   * @throws InvocationTargetException constructor throws exception
   */
  private static Object[] resolveConstructorArgs(final Class<?>[] parameterTypes)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    Object[] args = new Object[parameterTypes.length];
    for (int index = 0; index < parameterTypes.length; index++) {
      args[index] = resolveDependency(parameterTypes[index]);
    }
    return args;
  }

  /**
   * resolve dependency by type.
   *
   * @param targetClass class type
   * @return instance
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   * @throws InvocationTargetException constructor throws exception
   */
  private static Object resolveDependency(final Class<?> targetClass)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    Class<?> implementation = findImplementationClass(targetClass);
    return getOrCreate(implementation);
  }

  /**
   * get instance from container.
   *
   * @param targetClass class type
   * @param <T> interface class
   * @return instance
   */
  public static <T> T getBean(final Class<T> targetClass)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    log.debug("search for object for class:{}", targetClass.getName());

    Object match = findFromContainer(targetClass);
    if (match == null) {
      match = getOrCreate(findImplementationClass(targetClass));
    }

    return targetClass.cast(match);
  }

  /**
   * find unique instance in container.
   *
   * @param targetClass class type
   * @return instance or null
   */
  private static Object findFromContainer(final Class<?> targetClass) {
    Object match = null;

    for (Object value : container.values()) {
      Class<?> entryClass = value.getClass();
      if (!targetClass.isAssignableFrom(entryClass)) {
        continue;
      }

      log.debug("match container object.");
      if (match != null) {
        throw new IllegalStateException("not single object");
      }
      match = value;
    }

    return match;
  }

  /**
   * find unique implementation class for target type.
   *
   * @param targetClass class type
   * @return implementation class
   */
  private static Class<?> findImplementationClass(final Class<?> targetClass) {
    Class<?> match = null;

    for (Class<?> candidate : namedClasses) {
      if (!targetClass.isAssignableFrom(candidate)) {
        continue;
      }

      if (match != null) {
        throw new IllegalStateException("not single object");
      }
      match = candidate;
    }

    if (match == null) {
      throw new IllegalStateException("no object");
    }

    return match;
  }
}
