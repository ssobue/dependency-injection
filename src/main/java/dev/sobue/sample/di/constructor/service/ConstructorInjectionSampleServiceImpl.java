package dev.sobue.sample.di.constructor.service;

import dev.sobue.sample.di.constructor.repository.ConstructorInjectionSampleRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Sample Service Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class ConstructorInjectionSampleServiceImpl
    implements ConstructorInjectionSampleService {

  /**
   * Sample Repository.
   */
  private final ConstructorInjectionSampleRepository repository;

  /**
   * Constructor.
   *
   * @param repository repository
   */
  @Inject
  public ConstructorInjectionSampleServiceImpl(
      final ConstructorInjectionSampleRepository repository) {
    this.repository = repository;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String get(final String input) {
    return repository.get(input);
  }
}
