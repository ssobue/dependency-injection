package dev.sobue.sample.di.constructor.container;

import dev.sobue.sample.di.constructor.container.fixture.multiple.ConstructorMultipleRepository;
import dev.sobue.sample.di.constructor.container.fixture.success.ConstructorSuccessControllerImpl;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Context (Constructor Injection)")
class ContextUnitTest {

  @BeforeEach
  void setup() throws Exception {
    clearContext();
  }

  @Nested
  @DisplayName("initialize")
  class Initialize {

    @Test
    @DisplayName("injects constructor dependencies")
    void injectsDependencies() throws Exception {
      Context.initialize("dev.sobue.sample.di.constructor.container.fixture.success");

      ConstructorSuccessControllerImpl controller =
          Context.getBean(ConstructorSuccessControllerImpl.class);
      String result = controller.get("abc");

      assertEquals("abc", result);
    }

    @Test
    @DisplayName("throws when multiple implementations exist")
    void throwsWhenMultipleImplementationsExist() throws Exception {
      Context.initialize("dev.sobue.sample.di.constructor.container.fixture.multiple");

      IllegalStateException exception =
          assertThrows(
              IllegalStateException.class,
              () -> Context.getBean(ConstructorMultipleRepository.class));

      assertEquals("not single object", exception.getMessage());
    }

    @Test
    @DisplayName("throws when multiple @Inject constructors exist")
    void throwsWhenMultipleInjectConstructorsExist() {
      InstantiationException exception =
          assertThrows(
              InstantiationException.class,
              () -> Context.initialize(
                  "dev.sobue.sample.di.constructor.container.fixture.multipleinject"));

      assertEquals("multiple @Inject constructors", exception.getMessage());
    }

    @Test
    @DisplayName("throws when no default constructor exists")
    void throwsWhenNoDefaultConstructorExists() {
      InstantiationException exception =
          assertThrows(
              InstantiationException.class,
              () -> Context.initialize(
                  "dev.sobue.sample.di.constructor.container.fixture.noconstructor"));

      assertEquals("no default constructor", exception.getMessage());
    }
  }

  @Nested
  @DisplayName("getBean")
  class GetBean {

    @Test
    @DisplayName("throws when no object is registered")
    void throwsWhenNoObjectIsRegistered() {
      IllegalStateException exception =
          assertThrows(
              IllegalStateException.class,
              () -> Context.getBean(ConstructorSuccessControllerImpl.class));

      assertEquals("no object", exception.getMessage());
    }
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
}
