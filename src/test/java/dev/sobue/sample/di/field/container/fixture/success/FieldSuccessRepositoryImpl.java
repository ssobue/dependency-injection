package dev.sobue.sample.di.field.container.fixture.success;

import jakarta.inject.Named;

@Named
public class FieldSuccessRepositoryImpl implements FieldSuccessRepository {

  @Override
  public String get(String input) {
    return input;
  }
}
