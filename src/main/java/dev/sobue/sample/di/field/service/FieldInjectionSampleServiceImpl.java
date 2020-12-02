package dev.sobue.sample.di.field.service;

import dev.sobue.sample.di.field.repository.FieldInjectionSampleRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class FieldInjectionSampleServiceImpl implements FieldInjectionSampleService {

  /**
   * Sample Repository.
   */
  @Inject
  private FieldInjectionSampleRepository repository;

  /**
   * Default Constructor
   */
  public FieldInjectionSampleServiceImpl() {
  }

  /**
   * Constructor
   */
  public FieldInjectionSampleServiceImpl(FieldInjectionSampleRepository repository) {
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
