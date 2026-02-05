package dev.sobue.sample.di.constructor.container.fixture.multiple;

import jakarta.inject.Named;

@Named
public class ConstructorMultipleRepositoryA implements ConstructorMultipleRepository {

  @Override
  public String get(String input) {
    return input;
  }
}
