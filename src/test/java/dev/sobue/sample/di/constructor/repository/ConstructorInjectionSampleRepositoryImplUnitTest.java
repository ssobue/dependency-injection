package dev.sobue.sample.di.constructor.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("ConstructorInjectionSampleRepositoryImpl")
public class ConstructorInjectionSampleRepositoryImplUnitTest {

  private ConstructorInjectionSampleRepository repository;

  @BeforeEach
  void setup() {
    repository = new ConstructorInjectionSampleRepositoryImpl();
  }

  @Nested
  @DisplayName("get")
  class Get {

    @Test
    @DisplayName("throws NullPointerException when input is null")
    void throwsWhenInputIsNull() {
      try {
        repository.get(null);
        fail();
      } catch (NullPointerException e) {
        assertTrue(true);
      }
    }

    @ParameterizedTest
    @DisplayName("returns expected hash values")
    @CsvSource({
        "'',e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
        "abc,ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"
    })
    void returnsExpectedHashValues(String input, String answer) {
      assertEquals(answer, repository.get(input));
    }
  }
}
