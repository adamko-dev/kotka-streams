package dev.adamko.kotka.extensions

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import org.apache.kafka.common.serialization.ListDeserializer
import org.apache.kafka.common.serialization.ListSerializer
import org.apache.kafka.common.serialization.Serdes

class SerdesTests : FunSpec({

  context("ListSerde") {

    test("expect ListSerde returns a... list serde!") {
      val listSerde = ListSerde(Serdes.String())

      listSerde.shouldBeInstanceOf<Serdes.ListSerde<*>>()

      listSerde.deserializer().shouldBeInstanceOf<ListDeserializer<*>>()
      listSerde.serializer().shouldBeInstanceOf<ListSerializer<*>>()
    }
  }
})
