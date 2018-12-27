package jp.sobue.sample.di.field.controller;

import jp.sobue.sample.di.field.service.FieldInjectionSampleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldInjectionSampleControllerImplUnitTest {

  private FieldInjectionSampleController controller;

  private FieldInjectionSampleService fieldInjectionSampleService;

  @Before
  public void setup() {
    fieldInjectionSampleService = mock(FieldInjectionSampleService.class);
    controller = new FieldInjectionSampleControllerImpl(fieldInjectionSampleService);
  }

  @Test
  public void inputNull() {
    runTest(null);
  }

  @Test
  public void inputEmpty() {
    runTest("");
  }

  @Test
  public void inputNonEmpty() {
    runTest("abc");
  }

  private void runTest(String input) {
    when(fieldInjectionSampleService.get(anyString())).thenReturn(input);

    String result = controller.get(input);

    Assert.assertEquals(input, result);
  }
}
