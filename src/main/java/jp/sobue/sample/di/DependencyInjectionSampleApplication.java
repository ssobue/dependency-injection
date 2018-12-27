package jp.sobue.sample.di;

import jp.sobue.sample.di.container.Context;
import jp.sobue.sample.di.controller.SampleController;
import jp.sobue.sample.di.repository.SampleRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dependency Injection Sample Application.
 *
 * @author Sho Sobue
 */
public class DependencyInjectionSampleApplication {

  /** Logger. */
  private static final Logger logger =
      LoggerFactory.getLogger(DependencyInjectionSampleApplication.class);

  /**
   * Main Method.
   *
   * @param args command line argument
   */
  public static void main(final String... args)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    try {
      logger.info("start app");

      logger.info("manual object definition");
      Context.register(SampleRepositoryImpl.class.getSimpleName(), new SampleRepositoryImpl());

      logger.info("automated object definition, search for Implementation annotation");
      String[] basePackages = {"jp.sobue.sample.di.service", "jp.sobue.sample.di.controller"};
      Context.initialize(basePackages);

      logger.info("get object from container");
      SampleController controller = Context.getBean(SampleController.class);

      logger.info("execute controller");
      logger.info("result = " + controller.get("input"));

      logger.info("end app");

    } catch (Throwable t) {
      // IllegalStatus
      System.err.println(t.getMessage());
      t.printStackTrace();
      throw t;
    }
  }
}
