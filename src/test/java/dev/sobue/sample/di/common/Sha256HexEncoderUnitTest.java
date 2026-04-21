package dev.sobue.sample.di.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Sha256HexEncoder")
class Sha256HexEncoderUnitTest {

  @Test
  @DisplayName("private constructor prevents instantiation")
  void privateConstructorPreventsInstantiation() throws Exception {
    Constructor<Sha256HexEncoder> constructor = Sha256HexEncoder.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception =
        assertThrows(InvocationTargetException.class, constructor::newInstance);

    assertEquals("no instances", exception.getCause().getMessage());
  }

  @ParameterizedTest
  @DisplayName("encode returns expected hash values")
  @CsvSource({
      "'',e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
      "abc,ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"
  })
  void encodeReturnsExpectedHashValues(final String input, final String expected) {
    assertEquals(expected, Sha256HexEncoder.encode(input));
  }
}
