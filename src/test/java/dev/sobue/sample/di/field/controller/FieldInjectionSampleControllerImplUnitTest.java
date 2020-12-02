package dev.sobue.sample.di.field.controller;

import dev.sobue.sample.di.field.service.FieldInjectionSampleService;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(MockitoExtension.class)
public class FieldInjectionSampleControllerImplUnitTest {

  private FieldInjectionSampleController controller;

  private FieldInjectionSampleService fieldInjectionSampleService;

  @BeforeEach
  void setup() {
    fieldInjectionSampleService = mock(FieldInjectionSampleService.class);
    controller = new FieldInjectionSampleControllerImpl(fieldInjectionSampleService);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"abc"})
  void inputTest(String input) {
    // setup
    when(fieldInjectionSampleService.get(eq(input))).thenReturn(input);

    // when
    String result = controller.get(input);

    // then
    verify(fieldInjectionSampleService).get(eq(input));
    assertEquals(input, result);
  }
}
