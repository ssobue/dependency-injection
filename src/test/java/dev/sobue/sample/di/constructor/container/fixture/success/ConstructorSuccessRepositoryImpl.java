package dev.sobue.sample.di.constructor.container.fixture.success;

import jakarta.inject.Named;

@Named
public class ConstructorSuccessRepositoryImpl implements ConstructorSuccessRepository {

  @Override
  public String get(String input) {
    return input;
  }
}
