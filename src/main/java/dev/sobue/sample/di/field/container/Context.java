package dev.sobue.sample.di.field.container;

import dev.sobue.sample.di.common.ClassPathClassNameCollector;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * Dependency Injection Container Implementation.
 *
 * @author Sho Sobue
 */
@Slf4j
public class Context {

  /**
   * Trace log format for container state.
   */
  private static final String CONTAINER_LOG_FORMAT = "container:{}";

  /**
   * Message for missing no-arg constructor.
   */
  private static final String NO_DEFAULT_CONSTRUCTOR_MESSAGE = "no default constructor";

  /**
   * Prevent instantiation.
   */
  private Context() {
    throw new AssertionError("no instances");
  }

  /**
   * Container Object.
   */
  private static final Map<String, Object> container = new ConcurrentHashMap<>();

  // for trace log
  static {
    log.trace(CONTAINER_LOG_FORMAT, container);
  }

  /**
   * regist container by id.
   *
   * @param id name
   * @param object instance object
   */
  public static void register(final String id, final Object object) {
    container.put(id, object);
    log.trace(CONTAINER_LOG_FORMAT, container);
  }

  /**
   * initialize container: scan {@link Named} annotations and inject object to annotated
   * {@link Inject}.
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
    log.trace(CONTAINER_LOG_FORMAT, container);
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
      List<String> classes = ClassPathClassNameCollector.collect(packageName);
      log.debug("class list => basePackage:{} classes:{}", packageName, classes);

      // Load Named Annotated Classes
      for (String className : classes) {
        Class<?> clazz = Class.forName(className);
        if (clazz.isAnnotationPresent(Named.class)) {
          log.debug("class:{} is Named annotation presented, register to container", className);
          register(clazz.getSimpleName(), createInstance(clazz));
        }
      }
    }
  }

  /**
   * create instance from class.
   *
   * @param clazz target class
   * @return instance
   * @throws InstantiationException no default constructor
   * @throws IllegalAccessException not accessible class
   * @throws InvocationTargetException constructor throws exception
   */
  private static Object createInstance(final Class<?> clazz)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException e) {
      InstantiationException exception = new InstantiationException(NO_DEFAULT_CONSTRUCTOR_MESSAGE);
      exception.initCause(e);
      throw exception;
    }
  }

  /**
   * inject object to annotated {@link Inject} fields in class.
   */
  private static void injection() {
    container.values().forEach(Context::injectFields);
  }

  /**
   * inject object to annotated {@link Inject} fields in class.
   *
   * @param instance target instance
   */
  @SuppressWarnings("java:S3011")
  private static void injectFields(final Object instance) {
    Class<?> clazz = instance.getClass();
    log.debug("start injection => class:{}", clazz.getName());

    for (Field field : clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(Inject.class)) {
        log.debug(
            "Inject annotation is not presented. class:{} field:{}",
            clazz.getName(),
            field.getName());
        continue;
      }

      try {
        if (!field.canAccess(instance) && !field.trySetAccessible()) {
          throw new IllegalStateException(
              "field is not accessible: " + clazz.getName() + "#" + field.getName());
        }

        log.debug(
            "Inject annotation is presented. class:{} field:{}",
            clazz.getName(),
            field.getName());
        log.debug("start search object from container by class type");
        field.set(instance, getBean(field.getType()));
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            "failed to inject field: " + clazz.getName() + "#" + field.getName(),
            e);
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
  public static <T> T getBean(final Class<T> targetClass) {
    log.debug("search for object for class:{}", targetClass.getName());

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

    if (match == null) {
      throw new IllegalStateException("no object");
    }

    return targetClass.cast(match);
  }
}
