package dev.sobue.sample.di.field.repository;

import jakarta.inject.Named;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

/**
 * Sample Repository Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class FieldInjectionSampleRepositoryImpl implements FieldInjectionSampleRepository {

  /**
   * Hash Algorithm.
   */
  private static final String HASH_ALGORITHM = "SHA-256";

  /**
   * get by hash (SHA-256).
   *
   * @param input input value
   * @return get result
   * @throws IllegalArgumentException null presented.
   */
  @Override
  public String get(final String input) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance(HASH_ALGORITHM);
    } catch (Exception e) {
      return "";
    }

    byte[] result = digest.digest(input.getBytes());
    return Hex.encodeHexString(result);
  }
}
