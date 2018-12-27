package jp.sobue.sample.di.field.service;

import jp.sobue.sample.di.field.repository.FieldInjectionSampleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldInjectionSampleServiceImplUnitTest {

  private FieldInjectionSampleService service;

  private FieldInjectionSampleRepository repository;

  @Before
  public void setup() {
    repository = mock(FieldInjectionSampleRepository.class);
    service = new FieldInjectionSampleServiceImpl(repository);
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
    when(repository.get(anyString())).thenReturn(input);

    String result = service.get(input);

    Assert.assertEquals(input, result);
  }
}
