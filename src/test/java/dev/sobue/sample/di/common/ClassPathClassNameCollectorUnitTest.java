package dev.sobue.sample.di.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ClassPathClassNameCollector")
class ClassPathClassNameCollectorUnitTest {

  @Test
  @DisplayName("private constructor prevents instantiation")
  void privateConstructorPreventsInstantiation() throws Exception {
    Constructor<ClassPathClassNameCollector> constructor =
        ClassPathClassNameCollector.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception =
        assertThrows(InvocationTargetException.class, constructor::newInstance);

    assertEquals("no instances", exception.getCause().getMessage());
  }

  @Test
  @DisplayName("collect returns classes from nested packages")
  void collectReturnsClassesFromNestedPackages() {
    List<String> classes = ClassPathClassNameCollector.collect(
        "dev.sobue.sample.di.field.container.fixture");

    assertTrue(classes.contains(
        "dev.sobue.sample.di.field.container.fixture.success.FieldSuccessControllerImpl"));
    assertTrue(classes.contains(
        "dev.sobue.sample.di.field.container.fixture.noconstructor.FieldNoDefaultConstructorComponent"));
  }

  @Test
  @DisplayName("collect returns classes from a leaf package")
  void collectReturnsClassesFromLeafPackage() {
    List<String> classes = ClassPathClassNameCollector.collect(
        "dev.sobue.sample.di.field.container.fixture.success");

    assertEquals(5, classes.size());
    assertTrue(classes.contains(
        "dev.sobue.sample.di.field.container.fixture.success.FieldSuccessControllerImpl"));
    assertTrue(classes.contains(
        "dev.sobue.sample.di.field.container.fixture.success.FieldSuccessRepository"));
  }

  @Test
  @DisplayName("collect throws when package resource is missing")
  void collectThrowsWhenPackageResourceIsMissing() {
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> ClassPathClassNameCollector.collect("dev.sobue.sample.di.missing"));

    assertEquals("resource not found: dev/sobue/sample/di/missing", exception.getMessage());
  }
}
