package dev.adamko.kotka.kxs

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContainInOrder
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class StringFormatSerdeTest : FunSpec({

  context("toString()") {

    test("Int serializer") {
      val serde = Json.serde<Int>()
      serde.toString()
        .shouldContainInOrder(
          "StringFormat serde:",
          "kotlinx.serialization.internal.IntSerializer",
        )
    }

    test("TestDataClass serializer") {
      val serde = Json.serde<TestDataClass>()
      serde.toString()
        .shouldContainInOrder(
          "StringFormat serde:",
          "dev.adamko.kotka.kxs.StringFormatSerdeTest",
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
