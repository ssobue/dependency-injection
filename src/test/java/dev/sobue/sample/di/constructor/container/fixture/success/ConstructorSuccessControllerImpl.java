package dev.sobue.sample.di.constructor.container.fixture.success;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class ConstructorSuccessControllerImpl {

  private final ConstructorSuccessService service;

  @Inject
  public ConstructorSuccessControllerImpl(ConstructorSuccessService service) {
    this.service = service;
  }

  public String get(String input) {
    return service.get(input);
  }
}
