package dev.sobue.sample.di.constructor.container.fixture.multipleinject;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class ConstructorMultipleInjectComponent {

  @Inject
  public ConstructorMultipleInjectComponent() {
    // Intentionally empty to verify detection of multiple @Inject constructors in tests.
  }

  @Inject
  public ConstructorMultipleInjectComponent(String value) {
  }
}
