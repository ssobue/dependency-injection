package dev.sobue.sample.di.field.service;

import dev.sobue.sample.di.field.repository.FieldInjectionSampleRepository;
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

@DisplayName("FieldInjectionSampleServiceImpl")
@ExtendWith(MockitoExtension.class)
public class FieldInjectionSampleServiceImplUnitTest {

  private FieldInjectionSampleService service;

  private FieldInjectionSampleRepository repository;

  @BeforeEach
  void setup() {
    repository = mock(FieldInjectionSampleRepository.class);
    service = new FieldInjectionSampleServiceImpl(repository);
  }

  @Nested
  @DisplayName("get")
  class Get {

    @ParameterizedTest
    @DisplayName("returns repository result for null, empty, and non-empty input")
    @NullAndEmptySource
    @ValueSource(strings = {"abc"})
    void returnsRepositoryResult(String input) {
      // setup
      when(repository.get(eq(input))).thenReturn(input);

      // when
      String result = service.get(input);

      // then
      verify(repository).get(eq(input));
      assertEquals(input, result);
    }
  }
}
