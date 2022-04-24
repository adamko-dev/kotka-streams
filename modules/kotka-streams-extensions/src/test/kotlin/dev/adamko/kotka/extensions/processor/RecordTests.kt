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
  isolationMode = IsolationMode.InstancePerLeaf // so the mockk is fresh for each test

  Given("a Kafka Streams Processor API Record") {
    val mockRecord: Record<String, String> = mockk {
      every { key() } returns "record-key"
      every { value() } returns "record-value"
      every { timestamp() } returns 12345L
    }

    When("the Record extension functions are used") {

      Then("expect component1 returns the Record's key") {
        mockRecord.component1()

        verify(exactly = 1) { mockRecord.key() }
        confirmVerified(mockRecord)
      }

      Then("expect component2 should return the Record's value") {
        mockRecord.component2()

        verify(exactly = 1) { mockRecord.value() }
        confirmVerified(mockRecord)
      }

      Then("expect component3 should return the Record's timestamp") {
        mockRecord.component3()

        verify(exactly = 1) { mockRecord.timestamp() }
        confirmVerified(mockRecord)
      }

    }

    When("the Record is deconstructed") {
      val (key, value, timestamp) = mockRecord

      Then("expect the Record's key is extracted") {
        key shouldBe "record-key"
      }

      Then("expect the Record's value is extracted") {
        value shouldBe "record-value"
      }

      Then("expect the Record's timestamp is extracted") {
        timestamp shouldBe 12345L
      }

      Then("expect the extension functions are used") {
        verify(exactly = 1) { mockRecord.key() }
        verify(exactly = 1) { mockRecord.value() }
        verify(exactly = 1) { mockRecord.timestamp() }
        confirmVerified(mockRecord)
      }
    }

  }

})
