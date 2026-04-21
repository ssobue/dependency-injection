package dev.sobue.sample.di.field.controller;

import dev.sobue.sample.di.field.service.FieldInjectionSampleService;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class FieldInjectionSampleControllerImpl implements FieldInjectionSampleController {

  /**
   * Service. Field injection is intentional because this package demonstrates field-based DI.
   */
  @SuppressWarnings("java:S6813")
  @Inject
  private FieldInjectionSampleService service;

  /**
   * Default Constructor
   */
  public FieldInjectionSampleControllerImpl() {
  }

  /**
   * Constructor
   */
  public FieldInjectionSampleControllerImpl(FieldInjectionSampleService service) {
    this.service = service;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String get(final String input) {
    return service.get(input);
  }
}
