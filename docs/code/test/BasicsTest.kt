// This file was automatically generated from basics.md by Knit tool. Do not edit.
@file:Suppress("JSUnusedLocalSymbols")
package dev.adamko.kotka.example.test

import io.kotest.matchers.*
import kotlinx.knit.test.*
import org.junit.jupiter.api.Test

class BasicsTest {
  @Test
  @org.junit.jupiter.api.Disabled
  fun testExampleBasicsNamingOperators01() {
    captureOutput("ExampleBasicsNamingOperators01") {
      dev.adamko.kotka.example.exampleBasicsNamingOperators01.main()
    }.joinToString("\n")
      .trim()
      .shouldBe(
        // language=text
        """
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
        """.trimMargin()
      )
  }

  @Test
  @org.junit.jupiter.api.Disabled
  fun testExampleBasicsNamingOperators02() {
    captureOutput("ExampleBasicsNamingOperators02") {
      dev.adamko.kotka.example.exampleBasicsNamingOperators02.main()
    }.joinToString("\n")
      .trim()
      .shouldBe(
        // language=text
        """
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
        """.trimMargin()
      )
  }
}
