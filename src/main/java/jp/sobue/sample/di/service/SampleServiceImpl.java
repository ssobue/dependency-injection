package jp.sobue.sample.di.service;

import jp.sobue.sample.di.annotations.Implementation;
import jp.sobue.sample.di.annotations.InjectObject;
import jp.sobue.sample.di.repository.SampleRepository;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Implementation
public class SampleServiceImpl implements SampleService {

  /** Sample Repository. */
  @InjectObject private SampleRepository repository;

  /** Constructor */
  public SampleServiceImpl(SampleRepository repository) {
    this.repository = repository;
  }

  /** {@inheritDoc} */
  @Override
  public String get(final String input) {
    return repository.get(input);
  }
}
