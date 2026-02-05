package dev.sobue.sample.di.constructor;

import dev.sobue.sample.di.constructor.container.Context;
import dev.sobue.sample.di.constructor.controller.ConstructorInjectionSampleController;
import java.lang.reflect.InvocationTargetException;
import lombok.extern.slf4j.Slf4j;

/**
 * Dependency Injection Constructor Sample Application.
 *
 * @author Sho Sobue
 */
@Slf4j
public class ConstructorInjectionApplication {

  /**
   * Main Method.
   *
   * @param args command line argument
   */
  public static void main(final String... args)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException,
      InvocationTargetException {
    try {
      log.info("start app");

      log.info("automated object definition, search for Named annotation");
      Context.initialize(ConstructorInjectionApplication.class.getPackage().getName());

      log.info("-------- start constructor injection test run --------");

      log.info("get object from container");
      ConstructorInjectionSampleController controller =
          Context.getBean(ConstructorInjectionSampleController.class);

      log.info("execute controller");
      log.info("result = {}", controller.get("input"));

      log.info("-------- end constructor injection test run --------");

      log.info("end app");

    } catch (Throwable t) {
      // IllegalStatus
      System.err.println(t.getMessage());
      t.printStackTrace();
      throw t;
    }
  }
}
