package jp.sobue.sample.di.field.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jp.sobue.sample.di.field.repository.FieldInjectionSampleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FieldInjectionSampleServiceImplUnitTest {

  private FieldInjectionSampleService service;

  private FieldInjectionSampleRepository repository;

  @BeforeEach
  void setup() {
    repository = mock(FieldInjectionSampleRepository.class);
    service = new FieldInjectionSampleServiceImpl(repository);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"abc"})
  void inputTest(String input) {
    when(repository.get(anyString())).thenReturn(input);

    String result = service.get(input);

    Assertions.assertEquals(input, result);
  }
}
