package jp.sobue.sample.di.controller;

import javax.inject.Inject;
import javax.inject.Named;
import jp.sobue.sample.di.service.SampleService;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class SampleControllerImpl implements SampleController {

  /** Service. */
  @Inject private SampleService service;

  /** Default Constructor */
  public SampleControllerImpl() {}

  /** Constructor */
  public SampleControllerImpl(SampleService service) {
    this.service = service;
  }

  /** {@inheritDoc} */
  @Override
  public String get(final String input) {
    return service.get(input);
  }
}
