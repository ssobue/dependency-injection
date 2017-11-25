package jp.sobue.sample.di.controller;

import jp.sobue.sample.di.annotations.Autowired;
import jp.sobue.sample.di.annotations.Component;
import jp.sobue.sample.di.service.SampleService;

/**
 * Sample Controller Implementation
 *
 * @author Sho Sobue
 */
@Component
public class SampleControllerImpl implements SampleController {

  /**
   * Sample Service
   */
  @Autowired
  private SampleService sampleService;

  /**
   * get data
   *
   * @param input input value
   *
   * @return get result
   */
  @Override
  public String get(String input) {
    return sampleService.get(input);
  }
}
