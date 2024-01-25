package dev.adamko.kotka.kxs

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContainInOrder
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor

class BinaryFormatSerdeTest : FunSpec({

  context("toString()") {

    test("Int serializer") {
      val serde = Cbor.serde<Int>()
      serde.toString()
        .shouldContainInOrder(
          "BinaryFormat serde:",
          "kotlinx.serialization.internal.IntSerializer",
        )
    }

    test("TestDataClass serializer") {
      val serde = Cbor.serde<TestDataClass>()
      serde.toString()
        .shouldContainInOrder(
          "BinaryFormat serde:",
          "dev.adamko.kotka.kxs.BinaryFormatSerdeTest",
          "TestDataClass",
          "serializer",
        )
    }
  }

}) {

  @Serializable
  private data class TestDataClass(
    val int: Int,
    val double: Double,
    val string: String,
  )

}
