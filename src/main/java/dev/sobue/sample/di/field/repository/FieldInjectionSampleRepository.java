package dev.sobue.sample.di.field.repository;

/**
 * Sample Repository Interface.
 *
 * @author Sho Sobue
 */
public interface FieldInjectionSampleRepository {

  /**
   * get data.
   *
   * @param input input value
   * @return get result
   */
  String get(final String input);
}
