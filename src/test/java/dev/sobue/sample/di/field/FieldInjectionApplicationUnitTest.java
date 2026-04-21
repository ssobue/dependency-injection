package dev.sobue.sample.di.field;

import dev.sobue.sample.di.field.container.Context;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("FieldInjectionApplication")
class FieldInjectionApplicationUnitTest {

  @BeforeEach
  void setup() throws Exception {
    clearContainer();
  }

  @Test
  @DisplayName("default constructor creates instance")
  void defaultConstructorCreatesInstance() {
    assertNotNull(new FieldInjectionApplication());
  }

  @Test
  @DisplayName("main runs with null args")
  void mainRunsWithNullArgs() throws Exception {
    runWithMainClassesContextLoader(() ->
        assertDoesNotThrow(() -> FieldInjectionApplication.main((String[]) null)));
  }

  @Test
  @DisplayName("main runs with command line args")
  void mainRunsWithCommandLineArgs() throws Exception {
    runWithMainClassesContextLoader(() ->
        assertDoesNotThrow(() -> FieldInjectionApplication.main("first", "second")));
  }

  private static void clearContainer() throws Exception {
    Field field = Context.class.getDeclaredField("container");
    field.setAccessible(true);
    Map<?, ?> container = (Map<?, ?>) field.get(null);
    container.clear();
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
