package jp.sobue.sample.di.controller

import jp.sobue.sample.di.service.SampleService
import spock.lang.Specification
import spock.lang.Unroll

class SampleControllerImplUnitTest extends Specification {

  private SampleController controller

  def setup() {
    controller = new SampleControllerImpl()
    controller.sampleService = Mock(SampleService)
  }

  @Unroll
  def "get #testCase input:#input"() {
    when:
      def result = controller.get(input)

    then:
      1 * (controller as SampleControllerImpl).sampleService.get(input) >> { return input }

      assert result == input

    where:
      input || testCase
      null  || "nullの場合"
      ""    || "空文字の場合"
      "abc" || "文字列の場合"
  }
}