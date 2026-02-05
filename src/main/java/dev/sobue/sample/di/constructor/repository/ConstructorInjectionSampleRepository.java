package dev.sobue.sample.di.constructor.repository;

/**
 * Sample Repository Interface.
 *
 * @author Sho Sobue
 */
public interface ConstructorInjectionSampleRepository {

  /**
   * get by hash (SHA-256).
   *
   * @param input input value
   * @return get result
   * @throws IllegalArgumentException null presented.
   */
  String get(final String input);
}
