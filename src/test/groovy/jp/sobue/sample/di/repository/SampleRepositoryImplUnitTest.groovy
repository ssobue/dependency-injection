package jp.sobue.sample.di.repository

import spock.lang.Specification
import spock.lang.Unroll

class SampleRepositoryImplUnitTest extends Specification {

  private SampleRepository repository

  def setup() {
    repository = new SampleRepositoryImpl()
  }

  def "get nullの場合 input:null"() {
    when:
      repository.get(null)

    then:
      thrown(Exception)
  }

  @Unroll
  def "get #testCase input:#input answer:#answer"() {
    when:
      def result = repository.get(input)

    then:
      assert result == answer

    where:
      input || answer                                                             || testCase
      ""    || "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855" || "空文字の場合"
      "abc" || "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad" || "文字列の場合"
  }
}