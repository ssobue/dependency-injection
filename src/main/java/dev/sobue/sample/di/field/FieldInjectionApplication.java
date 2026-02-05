package dev.sobue.sample.di.field;

import dev.sobue.sample.di.field.container.Context;
import dev.sobue.sample.di.field.controller.FieldInjectionSampleController;
import java.lang.reflect.InvocationTargetException;
import lombok.extern.slf4j.Slf4j;

/**
 * Dependency Injection Sample Application.
 *
 * @author Sho Sobue
 */
@Slf4j
public class FieldInjectionApplication {

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
      Context.initialize(FieldInjectionApplication.class.getPackage().getName());

      log.info("-------- start field injection test run --------");

      log.info("get object from container");
      FieldInjectionSampleController controller =
          Context.getBean(FieldInjectionSampleController.class);

      log.info("execute controller");
      log.info("result = {}", controller.get("input"));

      log.info("-------- end field injection test run --------");

      log.info("end app");

    } catch (Throwable t) {
      // IllegalStatus
      System.err.println(t.getMessage());
      t.printStackTrace();
      throw t;
    }
  }
}
