package dev.sobue.sample.di.constructor.container.fixture.success;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class ConstructorSuccessServiceImpl implements ConstructorSuccessService {

  private final ConstructorSuccessRepository repository;

  @Inject
  public ConstructorSuccessServiceImpl(ConstructorSuccessRepository repository) {
    this.repository = repository;
  }

  @Override
  public String get(String input) {
    return repository.get(input);
  }
}
