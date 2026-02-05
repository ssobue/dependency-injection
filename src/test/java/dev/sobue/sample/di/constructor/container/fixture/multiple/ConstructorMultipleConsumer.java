package dev.sobue.sample.di.constructor.container.fixture.multiple;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class ConstructorMultipleConsumer {

  private final ConstructorMultipleRepository repository;

  @Inject
  public ConstructorMultipleConsumer(ConstructorMultipleRepository repository) {
    this.repository = repository;
  }

  public String get(String input) {
    return repository.get(input);
  }
}
