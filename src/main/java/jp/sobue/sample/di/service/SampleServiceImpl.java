package jp.sobue.sample.di.service;

import javax.inject.Inject;
import javax.inject.Named;
import jp.sobue.sample.di.repository.SampleRepository;

/**
 * Sample Controller Implementation.
 *
 * @author Sho Sobue
 */
@Named
public class SampleServiceImpl implements SampleService {

  /** Sample Repository. */
  @Inject private SampleRepository repository;

  /** Default Constructor */
  public SampleServiceImpl() {}

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
