package dev.sobue.sample.di.field.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FieldInjectionSampleRepositoryImplUnitTest {

  private FieldInjectionSampleRepository repository;

  @BeforeEach
  void setup() {
    repository = new FieldInjectionSampleRepositoryImpl();
  }

  @Test
  void inputNull() {
    try {
      repository.get(null);
      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  @ParameterizedTest
  @CsvSource({
      "'',e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
      "abc,ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"
  })
  void runTest(String input, String answer) {
    assertEquals(answer, repository.get(input));
  }
}
