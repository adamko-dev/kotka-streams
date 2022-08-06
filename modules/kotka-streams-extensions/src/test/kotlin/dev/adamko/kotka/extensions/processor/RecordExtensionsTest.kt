package dev.adamko.kotka.extensions.processor

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.apache.kafka.streams.processor.api.Record

class RecordExtensionsTest : FunSpec({

  context("verify componentN() functions") {
    val record = Record("key 123", "value 999", 12345L)


    test("Record.component1() should return Record.key()") {
      record.component1() shouldBe "key 123"
      record.component1() shouldBe record.key()
    }

    test("Record.component2() should return Record.value()") {
      record.component2() shouldBe "value 999"
      record.component2() shouldBe record.value()
    }

    test("Record.component3() should return Record.timestamp()") {
      record.component3() shouldBe 12345L
      record.component3() shouldBe record.timestamp()
    }

    test("destructing assignment should return (key, value, timestamp)") {
      val (key, value, timestamp) = record
      key shouldBe "key 123"
      key shouldBe record.key()

      value shouldBe "value 999"
      value shouldBe record.value()

      timestamp shouldBe 12345L
      timestamp shouldBe record.timestamp()
    }

  }
})
