package dev.sobue.sample.di.field.container.fixture.success;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class FieldSuccessServiceImpl implements FieldSuccessService {

  @Inject
  private FieldSuccessRepository repository;

  @Override
  public String get(String input) {
    return repository.get(input);
  }
}
