package dev.sobue.sample.di.constructor.controller;

import dev.sobue.sample.di.constructor.service.ConstructorInjectionSampleService;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class ConstructorInjectionSampleControllerImpl
    implements ConstructorInjectionSampleController {

  /**
   * Service.
   */
  private final ConstructorInjectionSampleService service;

  /**
   * Constructor.
   *
   * @param service service
   */
  @Inject
  public ConstructorInjectionSampleControllerImpl(
      final ConstructorInjectionSampleService service) {
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
