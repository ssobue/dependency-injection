package jp.sobue.sample.di.repository;

/**
 * Sample Repository Interface
 *
 * @author Sho Sobue
 */
public interface SampleRepository {

  /**
   * get data
   *
   * @param input input value
   *
   * @return get result
   */
  String get(String input);
}
