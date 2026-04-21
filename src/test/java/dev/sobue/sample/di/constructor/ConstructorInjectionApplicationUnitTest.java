package dev.sobue.sample.di.constructor;

import dev.sobue.sample.di.constructor.container.Context;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("ConstructorInjectionApplication")
class ConstructorInjectionApplicationUnitTest {

  @BeforeEach
  void setup() throws Exception {
    clearContext();
  }

  @Test
  @DisplayName("default constructor creates instance")
  void defaultConstructorCreatesInstance() {
    assertNotNull(new ConstructorInjectionApplication());
  }

  @Test
  @DisplayName("main runs with null args")
  void mainRunsWithNullArgs() throws Exception {
    runWithMainClassesContextLoader(() ->
        assertDoesNotThrow(() -> ConstructorInjectionApplication.main((String[]) null)));
  }

  @Test
  @DisplayName("main runs with command line args")
  void mainRunsWithCommandLineArgs() throws Exception {
    runWithMainClassesContextLoader(() ->
        assertDoesNotThrow(() -> ConstructorInjectionApplication.main("first", "second")));
  }

  private static void clearContext() throws Exception {
    Field containerField = Context.class.getDeclaredField("container");
    containerField.setAccessible(true);
    Map<?, ?> container = (Map<?, ?>) containerField.get(null);
    container.clear();

    Field namedClassesField = Context.class.getDeclaredField("namedClasses");
    namedClassesField.setAccessible(true);
    List<?> namedClasses = (List<?>) namedClassesField.get(null);
    namedClasses.clear();
  }

  private static void runWithMainClassesContextLoader(final ThrowingRunnable action) throws Exception {
    ClassLoader original = Thread.currentThread().getContextClassLoader();
    URL[] urls = {Path.of("target/classes").toUri().toURL()};

    try (URLClassLoader classLoader = new URLClassLoader(urls, null)) {
      Thread.currentThread().setContextClassLoader(classLoader);
      action.run();
    } finally {
      Thread.currentThread().setContextClassLoader(original);
    }
  }

  @FunctionalInterface
  private interface ThrowingRunnable {
    void run() throws Exception;
  }
}
