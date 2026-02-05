package dev.sobue.sample.di.constructor.container.fixture.multiple;

import jakarta.inject.Named;

@Named
public class ConstructorMultipleRepositoryB implements ConstructorMultipleRepository {

  @Override
  public String get(String input) {
    return input;
  }
}
