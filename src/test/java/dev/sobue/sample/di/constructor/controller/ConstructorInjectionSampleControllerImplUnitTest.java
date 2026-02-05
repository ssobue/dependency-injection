package dev.sobue.sample.di.constructor.controller;

import dev.sobue.sample.di.constructor.service.ConstructorInjectionSampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("ConstructorInjectionSampleControllerImpl")
@ExtendWith(MockitoExtension.class)
public class ConstructorInjectionSampleControllerImplUnitTest {

  private ConstructorInjectionSampleController controller;

  private ConstructorInjectionSampleService constructorInjectionSampleService;

  @BeforeEach
  void setup() {
    constructorInjectionSampleService = mock(ConstructorInjectionSampleService.class);
    controller = new ConstructorInjectionSampleControllerImpl(constructorInjectionSampleService);
  }

  @Nested
  @DisplayName("get")
  class Get {

    @ParameterizedTest
    @DisplayName("returns service result for null, empty, and non-empty input")
    @NullAndEmptySource
    @ValueSource(strings = {"abc"})
    void returnsServiceResult(String input) {
      // setup
      when(constructorInjectionSampleService.get(eq(input))).thenReturn(input);

      // when
      String result = controller.get(input);

      // then
      verify(constructorInjectionSampleService).get(eq(input));
      assertEquals(input, result);
    }
  }
}
