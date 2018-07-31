package jp.sobue.sample.di.controller;

import jp.sobue.sample.di.annotations.Implementation;
import jp.sobue.sample.di.annotations.InjectObject;
import jp.sobue.sample.di.service.SampleService;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Implementation
public class SampleControllerImpl implements SampleController {

  /** Sample Service. */
  @InjectObject private SampleService sampleService;

  /** Default Constructor */
  public SampleControllerImpl() {}

  /** Constructor */
  public SampleControllerImpl(SampleService sampleService) {
    this.sampleService = sampleService;
  }

  /** {@inheritDoc} */
  @Override
  public String get(final String input) {
    return sampleService.get(input);
  }
}
