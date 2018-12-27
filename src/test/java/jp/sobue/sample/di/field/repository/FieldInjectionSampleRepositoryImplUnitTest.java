package jp.sobue.sample.di.field.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldInjectionSampleRepositoryImplUnitTest {

  private FieldInjectionSampleRepository repository;

  @Before
  public void setup() {
    repository = new FieldInjectionSampleRepositoryImpl();
  }

  @Test
  public void inputNull() {
    try {
      repository.get(null);
      Assert.fail();
    } catch (NullPointerException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void inputEmpty() {
    runTest("", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
  }

  @Test
  public void inputNonEmpty() {
    runTest("abc", "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad");
  }

  private void runTest(String input, String answer) {
    String result = repository.get(input);

    Assert.assertEquals(answer, result);
  }
}
