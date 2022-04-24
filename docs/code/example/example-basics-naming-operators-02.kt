// This file was automatically generated from basics.md by Knit tool. Do not edit.
@file:Suppress("PackageDirectoryMismatch", "unused")
package dev.adamko.kotka.example.exampleBasicsNamingOperators02

import dev.adamko.kotka.extensions.*
import org.apache.kafka.streams.*
import dev.adamko.kotka.extensions.streams.*
import org.apache.kafka.streams.kstream.*

private val builder = StreamsBuilder()

fun main() { 

val stream: KStream<String, String> = builder
  .stream("input", Consumed.`as`("Customer_transactions_input_topic"))

stream
  .filter(
    { _, v -> v != "invalid_txn" },
    Named.`as`("filter_out_invalid_txns")
  )
  .mapValues(
    { _, v -> v.take(6) },
    Named.`as`("Map_values_to_first_6_characters")
  )
  .to("output", Produced.`as`("Mapped_transactions_output_topic"))

val stream: KStream<String, String> = builder
// no need for backticks 
  .stream("input", consumedAs("Customer_transactions_input_topic"))

stream
  // tasks can be named directly using a string, 
  // and the lambda expression can be placed outside the parentheses 
  .filter("filter_out_invalid_txns") { _, v ->
    v != "invalid_txn"
  }.mapValues("Map_values_to_first_6_characters") { _, v ->
    v.take(6)
  }.to("output", producedAs("Mapped_transactions_output_topic"))

  println(builder.build().describe())
}
