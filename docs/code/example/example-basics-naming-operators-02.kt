// This file was automatically generated from basics.md by Knit tool. Do not edit.
@file:Suppress("PackageDirectoryMismatch", "unused")
package dev.adamko.kotka.example.exampleBasicsNamingOperators02

import dev.adamko.kotka.extensions.*
import org.apache.kafka.streams.*
import dev.adamko.kotka.extensions.streams.*
import org.apache.kafka.streams.kstream.*

fun main() { 

val kafkaStreamBuilder = StreamsBuilder()
val kafkaTransactions: KStream<String, String> = kafkaStreamBuilder
  .stream("input", Consumed.`as`("Customer_transactions_input_topic"))

kafkaTransactions
  .filter(
    { _, v -> v != "invalid_txn" },
    Named.`as`("filter_out_invalid_txns")
  )
  .mapValues(
    { _, v -> v.take(6) },
    Named.`as`("Map_values_to_first_6_characters")
  )
  .to("output", Produced.`as`("Mapped_transactions_output_topic"))

val kotkaStreamBuilder = StreamsBuilder()
val kotkaTransactions: KStream<String, String> = kotkaStreamBuilder
  // no need for backticks 
  .stream("input", consumedAs("Customer_transactions_input_topic"))

kotkaTransactions
  // tasks can be named directly using a string, 
  // and the lambda expression can be placed outside the parentheses 
  .filter(name = "filter_out_invalid_txns") { _, value ->
    value != "invalid_txn"
  }
  .mapValues(name = "Map_values_to_first_6_characters") { _, value ->
    value.take(6)
  }
  .to("output", producedAs("Mapped_transactions_output_topic"))

  println("kafkaStreamBuilder described:")
  println(kafkaStreamBuilder.build().describe())
  println("~~~~~~")
  println("kotkaStreamBuilder described:")
  println(kotkaStreamBuilder.build().describe())
  println("~~~~~~")
}
