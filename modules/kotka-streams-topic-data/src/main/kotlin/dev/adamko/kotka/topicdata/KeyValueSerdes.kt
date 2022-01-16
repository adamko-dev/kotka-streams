package dev.adamko.kotka.topicdata


import dev.adamko.kotka.extensions.consumedAs
import dev.adamko.kotka.extensions.groupedAs
import dev.adamko.kotka.extensions.joinedAs
import dev.adamko.kotka.extensions.producedAs
import dev.adamko.kotka.extensions.repartitionedAs
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Joined
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.Repartitioned
import org.apache.kafka.streams.processor.StreamPartitioner
import org.apache.kafka.streams.processor.TimestampExtractor

/**
 * A pair of [Serde]s - [one for the Key][keySerde], [another for the Value][valueSerde].
 */
open class KeyValueSerdes<K, V>(
  open val keySerde: Serde<K>,
  open val valueSerde: Serde<V>,
) {

  fun consumer(
    name: String? = null,
    keySerde: Serde<K>? = this.keySerde,
    valueSerde: Serde<V>? = this.valueSerde,
    resetPolicy: Topology.AutoOffsetReset? = null,
    timestampExtractor: TimestampExtractor? = null,
  ): Consumed<K, V> =
    consumedAs(
      name = name,
      keySerde = keySerde,
      valueSerde = valueSerde,
      resetPolicy = resetPolicy,
      timestampExtractor = timestampExtractor,
    )

  fun grouper(
    name: String? = null,
    keySerde: Serde<K>? = this.keySerde,
    valueSerde: Serde<V>? = this.valueSerde,
  ): Grouped<K, V> = groupedAs(
    name = name,
    keySerde = keySerde,
    valueSerde = valueSerde,
  )

  fun <otherV> joiner(
    name: String? = null,
    keySerde: Serde<K>? = this.keySerde,
    valueSerde: Serde<V>? = this.valueSerde,
    otherValueSerde: Serde<otherV>?,
  ): Joined<K, V, otherV> = joinedAs(
    name = name,
    keySerde = keySerde,
    valueSerde = valueSerde,
    otherValueSerde = otherValueSerde,
  )

  fun producer(
    name: String? = null,
    keySerde: Serde<K>? = this.keySerde,
    valueSerde: Serde<V>? = this.valueSerde,
    partitioner: StreamPartitioner<K, V>? = null,
  ): Produced<K, V> = producedAs(
    name = name,
    keySerde = keySerde,
    valueSerde = valueSerde,
    partitioner = partitioner,
  )

  fun repartitioner(
    name: String? = null,
    keySerde: Serde<K>? = this.keySerde,
    valueSerde: Serde<V>? = this.valueSerde,
    numberOfPartitions: Int? = null,
    partitioner: StreamPartitioner<K, V>? = null,
  ): Repartitioned<K, V> = repartitionedAs(
    name = name,
    keySerde = keySerde,
    valueSerde = valueSerde,
    numberOfPartitions = numberOfPartitions,
    partitioner = partitioner,
  )

  companion object {

    fun string(): KeyValueSerdes<String, String> =
      KeyValueSerdes<String, String>(
        Serdes.String(),
        Serdes.String(),
      )
  }
}
