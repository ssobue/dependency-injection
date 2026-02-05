package dev.sobue.sample.di.field.container.fixture.noconstructor;

import jakarta.inject.Named;

@Named
public class FieldNoDefaultConstructorComponent {

  public FieldNoDefaultConstructorComponent(String value) {
  }
}
