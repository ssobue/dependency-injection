package dev.sobue.sample.di.field.container.fixture.success;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class FieldSuccessControllerImpl {

  @Inject
  private FieldSuccessService service;

  public String get(String input) {
    return service.get(input);
  }
}
