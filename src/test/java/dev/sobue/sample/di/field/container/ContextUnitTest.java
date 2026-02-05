package dev.sobue.sample.di.field.container;

import dev.sobue.sample.di.field.container.fixture.success.FieldSuccessControllerImpl;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Context (Field Injection)")
class ContextUnitTest {

  @BeforeEach
  void setup() throws Exception {
    clearContainer();
  }

  @Nested
  @DisplayName("initialize")
  class Initialize {

    @Test
    @DisplayName("injects field dependencies")
    void injectsDependencies() throws Exception {
      Context.initialize("dev.sobue.sample.di.field.container.fixture.success");

      FieldSuccessControllerImpl controller = Context.getBean(FieldSuccessControllerImpl.class);
      String result = controller.get("abc");

      assertEquals("abc", result);
    }

    @Test
    @DisplayName("throws when no default constructor exists")
    void throwsWhenNoDefaultConstructorExists() {
      InstantiationException exception =
          assertThrows(
              InstantiationException.class,
              () -> Context.initialize(
                  "dev.sobue.sample.di.field.container.fixture.noconstructor"));

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
          assertThrows(IllegalStateException.class, () -> Context.getBean(String.class));

      assertEquals("no object", exception.getMessage());
    }

    @Test
    @DisplayName("throws when multiple candidates exist")
    void throwsWhenMultipleCandidatesExist() {
      Context.register("a", new MultiServiceImplA());
      Context.register("b", new MultiServiceImplB());

      IllegalStateException exception =
          assertThrows(IllegalStateException.class, () -> Context.getBean(MultiService.class));

      assertEquals("not single object", exception.getMessage());
    }
  }

  private static void clearContainer() throws Exception {
    Field field = Context.class.getDeclaredField("container");
    field.setAccessible(true);
    Map<?, ?> container = (Map<?, ?>) field.get(null);
    container.clear();
  }

  private interface MultiService {
    String get(String input);
  }

  private static class MultiServiceImplA implements MultiService {

    @Override
    public String get(String input) {
      return input;
    }
  }

  private static class MultiServiceImplB implements MultiService {

    @Override
    public String get(String input) {
      return input;
    }
  }
}
