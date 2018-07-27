package jp.sobue.sample.di.repository;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

/**
 * Sample Repository Implementation.
 *
 * @author Sho Sobue
 */
public class SampleRepositoryImpl implements SampleRepository {

  /** Hash Algorithm. */
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
