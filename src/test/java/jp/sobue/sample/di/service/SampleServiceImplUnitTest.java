package jp.sobue.sample.di.service;

import jp.sobue.sample.di.repository.SampleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SampleServiceImplUnitTest {

  private SampleService service;

  private SampleRepository repository;

  @Before
  public void setup() {
    repository = mock(SampleRepository.class);
    service = new SampleServiceImpl(repository);
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
