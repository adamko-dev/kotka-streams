package dev.adamko.kotka.extensions

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.state.ValueAndTimestamp

class KeyValueTest : FunSpec({

  context("KeyValue<K, V>") {

    test("expect Pair toKeyValue() returns KeyValue") {

      val pair: Pair<String, String> = "key 123" to "value 999"

      val result: KeyValue<String, String> = pair.toKeyValue()

      result.key shouldBe "key 123"
      result.key shouldBe pair.first

      result.value shouldBe "value 999"
      result.value shouldBe pair.second
    }

    context("componentN() operators") {
      val keyValue = KeyValue.pair(
        "key 123",
        "value 999",
      )

      test("component1() should return key") {
        keyValue.component1() shouldBe "key 123"
        keyValue.component1() shouldBe keyValue.key
      }

      test("component2() should return value") {
        keyValue.component2() shouldBe "value 999"
        keyValue.component2() shouldBe keyValue.value
      }

      test("destructuring declaration should return (key, value)") {
        val (key, value) = keyValue

        key shouldBe "key 123"
        key shouldBe keyValue.key

        value shouldBe "value 999"
        value shouldBe keyValue.value
      }
    }
  }

  context("KeyValue<K, ValueAndTimestamp<V>>") {

    test("expect Triple toKeyValueTimestamp() returns KeyValueAndTimestamp") {

      val triple = Triple("key 123", "value 999", 12345L)

      val result: KeyValue<String, ValueAndTimestamp<String>> = triple.toKeyValueTimestamp()

      result.key shouldBe "key 123"
      result.key shouldBe triple.first

      result.value.value() shouldBe "value 999"
      result.value.value() shouldBe triple.second

      result.value.timestamp() shouldBe 12345L
      result.value.timestamp() shouldBe triple.third
    }

    context("componentN() operators") {
      val keyValueTimestamp = KeyValue.pair(
        "key 123",
        ValueAndTimestamp.make("value 999", 12345L)
      )

      test("component1() should return key") {
        keyValueTimestamp.component1() shouldBe "key 123"
        keyValueTimestamp.component1() shouldBe keyValueTimestamp.key
      }

      test("component2() should return value") {
        keyValueTimestamp.component2() shouldBe "value 999"
        keyValueTimestamp.component2() shouldBe keyValueTimestamp.value.value()
      }

      test("component3() should return timestamp") {
        keyValueTimestamp.component3() shouldBe 12345L
        keyValueTimestamp.component3() shouldBe keyValueTimestamp.value.timestamp()
      }

      test("destructuring declaration should return (key, value, timestamp)") {
        val (key, value, timestamp) = keyValueTimestamp

        key shouldBe "key 123"
        key shouldBe keyValueTimestamp.key

        value shouldBe "value 999"
        value shouldBe keyValueTimestamp.value.value()

        timestamp shouldBe 12345L
        timestamp shouldBe keyValueTimestamp.value.timestamp()
      }
    }

    test("timestamp extension val should return timestamp") {
      val keyValueTimestamp = KeyValue.pair(
        "key 123",
        ValueAndTimestamp.make("value 999", 12345L)
      )

      keyValueTimestamp.timestamp shouldBe 12345L
      keyValueTimestamp.timestamp shouldBe keyValueTimestamp.value.timestamp()
    }
  }


  context("ValueAndTimestamp<V>") {
    val valueAndTimestamp = ValueAndTimestamp.make("value 999", 12345L)

    test("component1() should return value") {
      valueAndTimestamp.component1() shouldBe "value 999"
      valueAndTimestamp.component1() shouldBe valueAndTimestamp.value()
    }

    test("component2() should return timestamp") {
      valueAndTimestamp.component2() shouldBe 12345L
      valueAndTimestamp.component2() shouldBe valueAndTimestamp.timestamp()
    }

    test("destructuring declaration should return (value, timestamp)") {
      val (value, timestamp) = valueAndTimestamp

      value shouldBe "value 999"
      value shouldBe valueAndTimestamp.value()

      timestamp shouldBe 12345L
      timestamp shouldBe valueAndTimestamp.timestamp()
    }
  }
})
