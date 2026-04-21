package dev.sobue.sample.di.field.repository;

import dev.sobue.sample.di.common.Sha256HexEncoder;
import jakarta.inject.Named;

/**
 * Sample Repository Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class FieldInjectionSampleRepositoryImpl implements FieldInjectionSampleRepository {

  /**
   * get by hash (SHA-256).
   *
   * @param input input value
   * @return get result
   * @throws IllegalArgumentException null presented.
   */
  @Override
  public String get(final String input) {
    return Sha256HexEncoder.encode(input);
  }
}
