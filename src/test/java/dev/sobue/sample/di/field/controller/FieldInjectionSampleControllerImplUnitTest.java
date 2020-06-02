package dev.sobue.sample.di.field.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.sobue.sample.di.field.service.FieldInjectionSampleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

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
    when(fieldInjectionSampleService.get(eq(input))).thenReturn(input);

    String result = controller.get(input);

    verify(fieldInjectionSampleService).get(eq(input));

    Assertions.assertEquals(input, result);
  }
}
