package dev.adamko.kotka.extensions.processor

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.kafka.streams.processor.api.Record

class RecordTests : BehaviorSpec({
  isolationMode = IsolationMode.InstancePerLeaf

  Given("a Kafka Streams Processor API Record") {
    val mockRecord: Record<String, String> = mockk {
      every { key() } returns "record-key"
      every { value() } returns "record-value"
      every { timestamp() } returns 12345L
    }

    When("when the Record extension functions are used") {

      Then("component1 should return the Record's key") {
        mockRecord.component1()

        verify(exactly = 1) { mockRecord.key() }
        confirmVerified(mockRecord)
      }

      Then("component2 should return the Record's value") {
        mockRecord.component2()

        verify(exactly = 1) { mockRecord.value() }
        confirmVerified(mockRecord)
      }

      Then("component3 should return the Record's timestamp") {
        mockRecord.component3()

        verify(exactly = 1) { mockRecord.timestamp() }
        confirmVerified(mockRecord)
      }

    }

    When("when the Record is deconstructed") {
      val (key, value, timestamp) = mockRecord

      Then("the Record's key should be assigned") {
        key shouldBe "record-key"
      }

      Then("the Record's value should be assigned") {
        value shouldBe "record-value"
      }

      Then("the Record's timestamp should be assigned") {
        timestamp shouldBe 12345L
      }

      Then("all extension functions should be used") {
        verify(exactly = 1) { mockRecord.key() }
        verify(exactly = 1) { mockRecord.value() }
        verify(exactly = 1) { mockRecord.timestamp() }
        confirmVerified(mockRecord)
      }
    }

  }

})
