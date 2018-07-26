package jp.sobue.sample.di.controller;

import jp.sobue.sample.di.annotations.Implementation;
import jp.sobue.sample.di.annotations.InjectObject;
import jp.sobue.sample.di.service.SampleService;

/**
 * Sample Controller Implementation
 *
 * @author Sho Sobue
 */
@Implementation
public class SampleControllerImpl implements SampleController {

  /**
   * Sample Service
   */
  @InjectObject
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
