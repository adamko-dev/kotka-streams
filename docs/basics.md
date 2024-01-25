<!--- TEST_NAME BasicsTest -->

**Table of contents**

<!--- TOC -->

  * [Naming operators](#naming-operators)
  * [Naming operators 2](#naming-operators-2)

<!--- END -->


<!--- INCLUDE .*\.kt
import dev.adamko.kotka.extensions.*
import org.apache.kafka.streams.*
import dev.adamko.kotka.extensions.streams.*
import org.apache.kafka.streams.kstream.*

fun main() { 
----- SUFFIX .*\.kt

  println("kafkaStreamBuilder described:")
  println(kafkaStreamBuilder.build().describe())
  println("~~~~~~")
  println("kotkaStreamBuilder described:")
  println(kotkaStreamBuilder.build().describe())
  println("~~~~~~")
}
-->

### Naming operators

https://kafka.apache.org/documentation/streams/developer-guide/dsl-topology-naming.html

* `Grouped`
* `StreamJoined`
* `Joined`
* `StreamJoined`
* `Materialized`
* `Named`

<table>
<tr>
<th>Plain Kafka Streams</th>
<th>Kotka Streams</th>
</tr>
<tr>
<td>

```kotlin
val kafkaStreamBuilder = StreamsBuilder()
val kafkaTransactions: KStream<String, String> = kafkaStreamBuilder
  .stream("input", Consumed.`as`("Customer_transactions_input_topic"))

kafkaTransactions
  .filter(
    { _, value -> value != "invalid_txn" },
    Named.`as`("filter_out_invalid_txns")
  )
  .mapValues(
    { _, value -> value.take(6) },
    Named.`as`("Map_values_to_first_6_characters")
  )
  .to("output", Produced.`as`("Mapped_transactions_output_topic"))
```

</td>
<td>

```kotlin
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
```


</td>
</tr>
</table>

> You can get the full code [here](./code/example/example-basics-naming-operators-01.kt).

Both examples produce the same topology:

```text
Topologies:
   Sub-topology: 0
    Source: Customer_transactions_input_topic (topics: [input])
      --> filter_out_invalid_txns
    Processor: filter_out_invalid_txns (stores: [])
      --> Map_values_to_first_6_characters
      <-- Customer_transactions_input_topic
    Processor: Map_values_to_first_6_characters (stores: [])
      --> Mapped_transactions_output_topic
      <-- filter_out_invalid_txns
    Sink: Mapped_transactions_output_topic (topic: output)
      <-- Map_values_to_first_6_characters
```

<!--- TEST -->

### Naming operators 2
 


```kotlin
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
```


```kotlin
val kotkaStreamBuilder = StreamsBuilder()
val kotkaTransactions: KStream<String, String> = kotkaStreamBuilder
  // no need for backticks 
  .stream("input", consumedAs("Customer_transactions_input_topic"))

kotkaTransactions
  // tasks can be named directly using a string, 
  // and the lambda expression can be placed outside the parentheses 
  .filter(name = "filter_out_invalid_txns") { _, v ->
    v != "invalid_txn"
  }.mapValues(name = "Map_values_to_first_6_characters") { _, v ->
    v.take(6)
  }.to("output", producedAs("Mapped_transactions_output_topic"))
```


> You can get the full code [here](./code/example/example-basics-naming-operators-02.kt).

Both examples produce the same topology:

```text
Topologies:
   Sub-topology: 0
    Source: Customer_transactions_input_topic (topics: [input])
      --> filter_out_invalid_txns
    Processor: filter_out_invalid_txns (stores: [])
      --> Map_values_to_first_6_characters
      <-- Customer_transactions_input_topic
    Processor: Map_values_to_first_6_characters (stores: [])
      --> Mapped_transactions_output_topic
      <-- filter_out_invalid_txns
    Sink: Mapped_transactions_output_topic (topic: output)
      <-- Map_values_to_first_6_characters
```

<!--- TEST -->
