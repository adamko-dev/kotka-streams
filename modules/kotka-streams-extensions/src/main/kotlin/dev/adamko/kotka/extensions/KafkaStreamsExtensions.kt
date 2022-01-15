package dev.adamko.kotka.extensions

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Branched
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Joined
import org.apache.kafka.streams.kstream.Named
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.Repartitioned
import org.apache.kafka.streams.processor.StreamPartitioner
import org.apache.kafka.streams.processor.TimestampExtractor

/** Helpful extensions & shortcuts for Kafka Streams */


/** @see [Named] */
fun namedAs(name: String): Named = Named.`as`(name)


/** @see Repartitioned */
fun <Key, Val> repartitionedAs(
  name: String? = null,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  numberOfPartitions: Int? = null,
  partitioner: StreamPartitioner<Key, Val>? = null,
): Repartitioned<Key, Val> {
  var repartitioned = Repartitioned.`as`<Key, Val>(name)
    .withKeySerde(keySerde)
    .withValueSerde(valueSerde)
    .withStreamPartitioner(partitioner)

  if (numberOfPartitions != null)
    repartitioned = repartitioned.withNumberOfPartitions(numberOfPartitions)

  return repartitioned
}


/** @see Produced */
fun <Key, Val> producedAs(
  name: String? = null,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  partitioner: StreamPartitioner<Key, Val>? = null,
): Produced<Key, Val> =
  Produced.`as`<Key, Val>(name)
    .withKeySerde(keySerde)
    .withValueSerde(valueSerde)
    .withStreamPartitioner(partitioner)


/** @see Joined */
fun <Key, Val, otherVal> joinedAs(
  name: String? = null,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  otherValueSerde: Serde<otherVal>?,
): Joined<Key, Val, otherVal> =
  Joined.`as`<Key, Val, otherVal>(name)
    .withKeySerde(keySerde)
    .withValueSerde(valueSerde)
    .withOtherValueSerde(otherValueSerde)


/** @see [Grouped.`as`] */
fun <Key, Val> groupedAs(
  name: String? = null,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
): Grouped<Key, Val> =
  Grouped.`as`<Key, Val>(name)
    .withKeySerde(keySerde)
    .withValueSerde(valueSerde)


/** @see [Consumed.`as`] */
fun <Key, Val> consumedAs(
  name: String? = null,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  resetPolicy: Topology.AutoOffsetReset? = null,
  timestampExtractor: TimestampExtractor? = null,
): Consumed<Key, Val> =
  Consumed.`as`<Key, Val>(name)
    .withKeySerde(keySerde)
    .withValueSerde(valueSerde)
    .withOffsetResetPolicy(resetPolicy)
    .withTimestampExtractor(timestampExtractor)


fun <Key, Val> branchedAs(name: String): Branched<Key, Val> = Branched.`as`(name)
