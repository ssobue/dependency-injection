package jp.sobue.sample.di.service

import jp.sobue.sample.di.repository.SampleRepository
import spock.lang.Specification
import spock.lang.Unroll

class SampleServiceImplSTest extends Specification {

  private SampleService service

  def setup() {
    service = new SampleServiceImpl()
    service.repository = Mock(SampleRepository)

  }

  @Unroll
  def "get #testCase input:#input"() {
    when:
      def result = service.get(input)
    then:
      1 * service.repository.get(input) >> { return input }

      assert result == input

    where:
      input || testCase
      null  || "nullの場合"
      ""    || "空文字の場合"
      "abc" || "文字列の場合"
  }
}