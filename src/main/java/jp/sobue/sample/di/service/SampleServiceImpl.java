package jp.sobue.sample.di.service;

import jp.sobue.sample.di.annotations.InjectObject;
import jp.sobue.sample.di.annotations.Implementation;
import jp.sobue.sample.di.repository.SampleRepository;

/**
 * Sample Controller Interface
 *
 * @author Sho Sobue
 */
@Implementation
public class SampleServiceImpl implements SampleService {

  /** Sample Repository */
  @InjectObject private SampleRepository repository;

  /**
   * get data
   *
   * @param input input value
   * @return get result
   */
  @Override
  public String get(String input) {
    return repository.get(input);
  }
}
