package dev.adamko.kotka.extensions.streams

import dev.adamko.kotka.extensions.materializedAs
import io.kotest.core.spec.style.FunSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.Aggregator
import org.apache.kafka.streams.kstream.Initializer
import org.apache.kafka.streams.kstream.KGroupedStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Named
import org.apache.kafka.streams.kstream.Reducer
import org.apache.kafka.streams.state.KeyValueStore


class KGroupedStreamTest : FunSpec({

  context(".count() extension") {

    val kGroupedStream: KGroupedStream<String, String> = mockk {
      every { count(any(), any()) } returns mockk()
      every { count(any<Named>()) } returns mockk()
      every { count(any<Materialized<String, Long, KeyValueStore<Bytes, ByteArray>>>()) } returns mockk()
      every { count() } returns mockk()
    }

    test("name=null, materialized=null -> expect count() is called") {

      kGroupedStream.count(
        name = null,
        materialized = null
      )

      verify(exactly = 1) { kGroupedStream.count() }

      confirmVerified(kGroupedStream)
    }

    test("name='some-name', materialized=null -> expect count(namedAs('some-name')) is called") {

      kGroupedStream.count(
        name = "some-name",
        materialized = null
      )

      verify(exactly = 1) { kGroupedStream.count(any<Named>()) }

      confirmVerified(kGroupedStream)
    }

    test("name=null, materialized=Materialized<> -> expect count(Materialized<>) is called") {

      kGroupedStream.count(
        name = null,
        materialized = materializedAs("store-name"),
      )

      verify(exactly = 1) {
        kGroupedStream.count(any<Materialized<String, Long, KeyValueStore<Bytes, ByteArray>>>())
      }

      confirmVerified(kGroupedStream)
    }

    test("name='some-name', materialized=Materialized<> -> expect count('some-name', Materialized<>) is called") {

      kGroupedStream.count(
        name = "some-name",
        materialized = materializedAs("store-name"),
      )

      verify(exactly = 1) {
        kGroupedStream.count(
          any(),
          any<Materialized<String, Long, KeyValueStore<Bytes, ByteArray>>>()
        )
      }

      confirmVerified(kGroupedStream)
    }
  }


  context(".aggregate() extension") {

    val kGroupedStream: KGroupedStream<String, String> = mockk {
      every { aggregate(any<Initializer<String>>(), any(), any()) } returns mockk()

      every { aggregate(any<Initializer<String>>(), any(), any(), any()) } returns mockk()
    }

    test("verify aggregate extension (without name) is called") {

      kGroupedStream.aggregate(
        initializer = { "initial" },
        materialized = materializedAs("store-name"),
        aggregator = { key, value, aggregate -> "aggregated $key $value $aggregate" }
      )

      verify(exactly = 1) {
        kGroupedStream.aggregate(
          any<Initializer<Long>>(),
          any<Aggregator<in String, in String, Long>>(),
          any<Materialized<String, Long, KeyValueStore<Bytes, ByteArray>>>()
        )
      }

      confirmVerified(kGroupedStream)
    }

    test("verify aggregate extension (with name) is called") {

      kGroupedStream.aggregate(
        name = "test-name",
        initializer = { "initial" },
        materialized = materializedAs("store-name"),
        aggregator = { key, value, aggregate -> "aggregated $key $value $aggregate" }
      )

      verify(exactly = 1) {
        kGroupedStream.aggregate(
          any<Initializer<Long>>(),
          any<Aggregator<in String, in String, Long>>(),
          any(),
          any<Materialized<String, Long, KeyValueStore<Bytes, ByteArray>>>()
        )
      }

      confirmVerified(kGroupedStream)
    }
  }


  context(".reduce() extension") {

    val kGroupedStream: KGroupedStream<String, String> = mockk {
      every {
        reduce(
          any<Reducer<String>>(),
          any<Materialized<String, String, KeyValueStore<Bytes, ByteArray>>>(),
        )
      } returns mockk()

      every {
        reduce(
          any<Reducer<String>>(),
          any(),
          any<Materialized<String, String, KeyValueStore<Bytes, ByteArray>>>(),
        )
      } returns mockk()
    }

    test("verify reduce extension (without name) is called") {

      kGroupedStream.reduce(
        materialized = materializedAs("store-name"),
        reducer = { v1, v2 -> "reduced $v1 $v2" }
      )

      verify(exactly = 1) {
        kGroupedStream.reduce(
          any<Reducer<String>>(),
          any<Materialized<String, String, KeyValueStore<Bytes, ByteArray>>>(),
        )
      }

      confirmVerified(kGroupedStream)
    }

    test("verify aggregate extension (with name) is called") {

      kGroupedStream.reduce(
        name = "test-name",
        materialized = materializedAs("store-name"),
        reducer = { v1, v2 -> "reduced $v1 $v2" }
      )

      verify(exactly = 1) {
        kGroupedStream.reduce(
          any<Reducer<String>>(),
          any(),
          any<Materialized<String, String, KeyValueStore<Bytes, ByteArray>>>(),
        )
      }

      confirmVerified(kGroupedStream)
    }
  }
})
