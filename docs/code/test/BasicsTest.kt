// Knit tool automatically generated this file from basics.md. Do not edit.
@file:Suppress("JSUnusedLocalSymbols")
package dev.adamko.kotka.example.test

import io.kotest.matchers.*
import kotlinx.knit.test.*
import org.junit.jupiter.api.Test

class BasicsTest {
  @Test
  fun testExampleBasicsNamingOperators01() {

    // language=text
    val expected = """
    |Topologies:
    |   Sub-topology: 0
    |    Source: Customer_transactions_input_topic (topics: [input])
    |      --> filter_out_invalid_txns
    |    Processor: filter_out_invalid_txns (stores: [])
    |      --> Map_values_to_first_6_characters
    |      <-- Customer_transactions_input_topic
    |    Processor: Map_values_to_first_6_characters (stores: [])
    |      --> Mapped_transactions_output_topic
    |      <-- filter_out_invalid_txns
    |    Sink: Mapped_transactions_output_topic (topic: output)
    |      <-- Map_values_to_first_6_characters
    """.trimMargin().trim()

    val output = captureOutput("ExampleBasicsNamingOperators01") {
      dev.adamko.kotka.example.exampleBasicsNamingOperators01.main()
    }
      .joinToString("\n")

    val kafkaStreamDescription = output
      .substringAfter("kafkaStreamBuilder described:", "")
      .substringBefore("~~~~~~", missingDelimiterValue = "")
      .trim()
    val kotkaStreamDescription = output
      .substringAfter("kotkaStreamBuilder described:", "")
      .substringBefore("~~~~~~", missingDelimiterValue = "")
      .trim()

    kafkaStreamDescription.shouldBe(expected)
    kafkaStreamDescription.shouldBe(expected)
    kafkaStreamDescription.shouldBe(kotkaStreamDescription)

  }

  @Test
  fun testExampleBasicsNamingOperators02() {

    // language=text
    val expected = """
    |Topologies:
    |   Sub-topology: 0
    |    Source: Customer_transactions_input_topic (topics: [input])
    |      --> filter_out_invalid_txns
    |    Processor: filter_out_invalid_txns (stores: [])
    |      --> Map_values_to_first_6_characters
    |      <-- Customer_transactions_input_topic
    |    Processor: Map_values_to_first_6_characters (stores: [])
    |      --> Mapped_transactions_output_topic
    |      <-- filter_out_invalid_txns
    |    Sink: Mapped_transactions_output_topic (topic: output)
    |      <-- Map_values_to_first_6_characters
    """.trimMargin().trim()

    val output = captureOutput("ExampleBasicsNamingOperators02") {
      dev.adamko.kotka.example.exampleBasicsNamingOperators02.main()
    }
      .joinToString("\n")

    val kafkaStreamDescription = output
      .substringAfter("kafkaStreamBuilder described:", "")
      .substringBefore("~~~~~~", missingDelimiterValue = "")
      .trim()
    val kotkaStreamDescription = output
      .substringAfter("kotkaStreamBuilder described:", "")
      .substringBefore("~~~~~~", missingDelimiterValue = "")
      .trim()

    kafkaStreamDescription.shouldBe(expected)
    kafkaStreamDescription.shouldBe(expected)
    kafkaStreamDescription.shouldBe(kotkaStreamDescription)

  }
}
