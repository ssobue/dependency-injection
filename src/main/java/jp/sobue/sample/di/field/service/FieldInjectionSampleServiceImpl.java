package jp.sobue.sample.di.field.service;

import javax.inject.Inject;
import javax.inject.Named;
import jp.sobue.sample.di.field.repository.FieldInjectionSampleRepository;

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
