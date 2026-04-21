package dev.sobue.sample.di.common;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Encodes input text to a SHA-256 hexadecimal string.
 *
 * @author Sho Sobue
 */
public final class Sha256HexEncoder {

  /**
   * Prevent instantiation.
   */
  private Sha256HexEncoder() {
    throw new AssertionError("no instances");
  }

  /**
   * Encode text as SHA-256 hexadecimal.
   *
   * @param input input text
   * @return SHA-256 hexadecimal string
   */
  public static String encode(final String input) {
    return DigestUtils.sha256Hex(input);
  }
}
