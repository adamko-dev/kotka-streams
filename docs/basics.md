<!--- TEST_NAME BasicsTest -->

**Table of contents**

<!--- TOC -->

  * [Naming operators](#naming-operators)

<!--- END -->


<!--- INCLUDE .*\.kt
import dev.adamko.kotka.extensions.*
import org.apache.kafka.streams.*
import dev.adamko.kotka.extensions.streams.*
import org.apache.kafka.streams.kstream.*

private val builder = StreamsBuilder()

fun main() { 
----- SUFFIX .*\.kt

  println(builder.build().describe())
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

```kotlin
  val stream: KStream<String, String> = builder
    .stream("input", consumedAs("Customer_transactions_input_topic"))
  
  stream
    .filter("filter_out_invalid_txns") { _, v ->
      v != "invalid_txn"
    }.mapValues("Map_values_to_first_6_characters") { _, v ->
      v.take(6)
    }.to("output", producedAs("Mapped_transactions_output_topic"))
```

> You can get the full code [here](./code/example/example-basics-naming-operators-01.kt).
> 
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
