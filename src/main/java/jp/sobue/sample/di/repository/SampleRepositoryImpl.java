package jp.sobue.sample.di.repository;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * Sample Repository Implementation
 *
 * @author Sho Sobue
 */
public class SampleRepositoryImpl implements SampleRepository {

  /** Hash Algorithm */
  private static final String HASH_ALGORITHM = "SHA-256";

  /**
   * get by hash (SHA-256)
   *
   * @param input input value
   * @return get result
   * @throws IllegalArgumentException null presented.
   */
  @Override
  public String get(String input) {
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
