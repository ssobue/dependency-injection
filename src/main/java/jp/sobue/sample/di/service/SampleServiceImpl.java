package jp.sobue.sample.di.service;

import jp.sobue.sample.di.annotations.Autowired;
import jp.sobue.sample.di.annotations.Component;
import jp.sobue.sample.di.repository.SampleRepository;

/**
 * Sample Controller Interface
 *
 * @author Sho Sobue
 */
@Component
public class SampleServiceImpl implements SampleService {

  /**
   * Sample Repository
   */
  @Autowired
  private SampleRepository repository;

  /**
   * get data
   *
   * @param input input value
   *
   * @return get result
   */
  @Override
  public String get(String input) {
    return repository.get(input);
  }
}
