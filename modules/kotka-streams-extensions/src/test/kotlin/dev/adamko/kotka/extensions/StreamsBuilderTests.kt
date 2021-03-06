package dev.adamko.kotka.extensions

import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed

class StreamsBuilderTests : FunSpec({

  context("verify StreamBuilder.stream() with vararg topics calls stream() with List<>") {
    val topicsArb = Arb.list(Arb.string())


    test("single topic") {
      val builderMock: StreamsBuilder = mockk {
        every { stream<String, String>(any<Collection<String>>()) } returns mockk()
      }

      builderMock.stream<String, String>(consumed = null, "test-topic")

      verify(exactly = 1) {
        builderMock.stream<String, String>(listOf("test-topic"))
      }
      confirmVerified(builderMock)
    }


    test("multiple topics") {
      val builderMock: StreamsBuilder = mockk {
        every { stream<String, String>(any<Collection<String>>()) } returns mockk()
      }

      builderMock.stream<String, String>(
        "test-topic-1",
        "test-topic-2",
        "test-topic-3",
      )

      verify(exactly = 1) {
        builderMock.stream<String, String>(
          listOf(
            "test-topic-1",
            "test-topic-2",
            "test-topic-3",
          )
        )
      }
      confirmVerified(builderMock)
    }


    test("typed array, without Consumed") {
      checkAll(topicsArb) { topics ->
        val builderMock: StreamsBuilder = mockk {
          every { stream<String, String>(any<Collection<String>>()) } returns mockk()
        }

        builderMock.stream<String, String>(topics = topics.toTypedArray())

        verify(exactly = 1) { builderMock.stream<String, String>(topics) }
        confirmVerified(builderMock)
      }
    }


    test("typed array, with Consumed") {
      checkAll(topicsArb) { topics ->
        val builderMock: StreamsBuilder = mockk {
          every {
            stream(any<Collection<String>>(), any<Consumed<String, String>>())
          } returns mockk()
        }
        val consumed: Consumed<String, String> = mockk()

        builderMock.stream(topics = topics.toTypedArray(), consumed = consumed)

        verify(exactly = 1) { builderMock.stream(topics, consumed) }
        confirmVerified(builderMock)
      }
    }
  }

})
