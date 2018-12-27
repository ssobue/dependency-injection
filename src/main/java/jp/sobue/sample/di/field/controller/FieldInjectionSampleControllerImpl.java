package jp.sobue.sample.di.field.controller;

import javax.inject.Inject;
import javax.inject.Named;
import jp.sobue.sample.di.field.service.FieldInjectionSampleService;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class FieldInjectionSampleControllerImpl implements FieldInjectionSampleController {

  /** Service. */
  @Inject private FieldInjectionSampleService service;

  /** Default Constructor */
  public FieldInjectionSampleControllerImpl() {}

  /** Constructor */
  public FieldInjectionSampleControllerImpl(FieldInjectionSampleService service) {
    this.service = service;
  }

  /** {@inheritDoc} */
  @Override
  public String get(final String input) {
    return service.get(input);
  }
}
