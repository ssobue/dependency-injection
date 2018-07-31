package jp.sobue.sample.di.controller;

import jp.sobue.sample.di.service.SampleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SampleControllerImplUnitTest {

  private SampleController controller;

  private SampleService sampleService;

  @Before
  public void setup() {
    sampleService = mock(SampleService.class);
    controller = new SampleControllerImpl(sampleService);
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
    when(sampleService.get(anyString())).thenReturn(input);

    String result = controller.get(input);

    Assert.assertEquals(input, result);
  }
}
