package dev.sobue.sample.di.field;

import dev.sobue.sample.di.field.container.Context;
import dev.sobue.sample.di.field.controller.FieldInjectionSampleController;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dependency Injection Sample Application.
 *
 * @author Sho Sobue
 */
public class DependencyInjectionSampleApplication {

  /**
   * Logger.
   */
  private static final Logger logger =
      LoggerFactory.getLogger(DependencyInjectionSampleApplication.class);

  /**
   * Main Method.
   *
   * @param args command line argument
   */
  public static void main(final String... args)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException,
      InvocationTargetException {
    try {
      logger.info("start app");

      logger.info("automated object definition, search for Named annotation");
      Context.initialize(DependencyInjectionSampleApplication.class.getPackage().getName());

      logger.info("-------- start field injection test run --------");

      logger.info("get object from container");
      FieldInjectionSampleController controller =
          Context.getBean(FieldInjectionSampleController.class);

      logger.info("execute controller");
      logger.info("result = " + controller.get("input"));

      logger.info("-------- end field injection test run --------");

      logger.info("end app");

    } catch (Throwable t) {
      // IllegalStatus
      System.err.println(t.getMessage());
      t.printStackTrace();
      throw t;
    }
  }
}
