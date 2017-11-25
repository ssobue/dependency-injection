package jp.sobue.sample.di;

import jp.sobue.sample.di.container.DependencyInjectionContainer;
import jp.sobue.sample.di.controller.SampleController;
import jp.sobue.sample.di.repository.SampleRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dependency Injection Sample Application
 *
 * @author Sho Sobue
 */
public class DependencyInjectionSampleApplication {

  /**
   * Logger
   */
  private static final Logger logger = LoggerFactory.getLogger(DependencyInjectionSampleApplication.class);

  /**
   * Main Method
   *
   * @param args command line argument
   */
  public static void main(String... args) {
    try {
      logger.info("start app");

      logger.info("manual object definition");
      DependencyInjectionContainer.registObject(SampleRepositoryImpl.class.getSimpleName(), new SampleRepositoryImpl());

      logger.info("automated object definition, such as component scan of spring framework");
      String[] basePackages = {"jp.sobue.sample.di.service", "jp.sobue.sample.di.controller"};
      DependencyInjectionContainer.init(basePackages);

      logger.info("get object from container");
      SampleController controller = DependencyInjectionContainer.getObject(SampleController.class);

      logger.info("execute controller");
      logger.info("result = " + controller.get("input"));

      logger.info("end app");

    } catch (Throwable t) {
      // IllegalStatus
      System.err.println(t.getMessage());
      t.printStackTrace();
      System.exit(1);
    }
  }
}
