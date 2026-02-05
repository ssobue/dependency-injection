package dev.sobue.sample.di.constructor.container.fixture.noconstructor;

import jakarta.inject.Named;

@Named
public class ConstructorNoDefaultConstructorComponent {

  public ConstructorNoDefaultConstructorComponent(String value) {
  }
}
