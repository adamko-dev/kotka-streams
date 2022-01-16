package dev.adamko.kotka.extensions.tables

import dev.adamko.kotka.extensions.namedAs
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KGroupedTable
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable
import org.apache.kafka.streams.kstream.KeyValueMapper
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.ValueJoiner
import org.apache.kafka.streams.state.KeyValueStore


/** @see [KTable.mapValues] */
fun <K, inV, outV> KTable<K, inV>.mapValues(
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  mapper: (readOnlyKey: K, value: inV) -> outV,
): KTable<K, outV> {
  return when {
    name != null && materialized != null -> mapValues(mapper, namedAs(name), materialized)
    name != null && materialized == null -> mapValues(mapper, namedAs(name))
    name == null && materialized != null -> mapValues(mapper, materialized)
    else                                 -> mapValues(mapper)
  }
}


/** @See [KTable.groupBy] */
fun <inK, inV, outK, outV> KTable<inK, inV>.groupBy(
  grouped: Grouped<outK, outV>? = null,
  selector: KeyValueMapper<inK, inV, KeyValue<outK, outV>>
): KGroupedTable<outK, outV> = when (grouped) {
  null -> groupBy(selector)
  else -> groupBy(selector, grouped)
}


/** @See [KTable.join] */
fun <K, V, otherV, outV> KTable<K, V>.join(
  other: KTable<K, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    name != null && materialized != null -> join(other, joiner, namedAs(name), materialized)
    name != null && materialized == null -> join(other, joiner, namedAs(name))
    name == null && materialized != null -> join(other, joiner, materialized)
    else                                 -> join(other, joiner)
  }
}


/** @See [KTable.join] */
fun <K, V, otherK, otherV, outV> KTable<K, V>.join(
  other: KTable<otherK, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  foreignKeyExtractor: (V) -> otherK,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    name != null && materialized != null ->
      join(other, foreignKeyExtractor, joiner, namedAs(name), materialized)
    name != null && materialized == null ->
      join(other, foreignKeyExtractor, joiner, namedAs(name))
    name == null && materialized != null ->
      join(other, foreignKeyExtractor, joiner, materialized)
    else                                 ->
      join(other, foreignKeyExtractor, joiner)
  }
}


/** @See [KTable.leftJoin] */
fun <K, V, otherV, outV> KTable<K, V>.leftJoin(
  other: KTable<K, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    name != null && materialized != null -> leftJoin(other, joiner, namedAs(name), materialized)
    name != null && materialized == null -> leftJoin(other, joiner, namedAs(name))
    name == null && materialized != null -> leftJoin(other, joiner, materialized)
    else                                 -> leftJoin(other, joiner)
  }
}


/** @See [KTable.leftJoin] */
fun <K, V, otherK, otherV, outV> KTable<K, V>.leftJoin(
  other: KTable<otherK, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  foreignKeyExtractor: (V) -> otherK,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    name != null && materialized != null ->
      leftJoin(other, foreignKeyExtractor, joiner, namedAs(name), materialized)
    name != null && materialized == null ->
      leftJoin(other, foreignKeyExtractor, joiner, namedAs(name))
    name == null && materialized != null ->
      leftJoin(other, foreignKeyExtractor, joiner, materialized)
    else                                 ->
      leftJoin(other, foreignKeyExtractor, joiner)
  }
}


/** @See [KTable.outerJoin] */
fun <K, V, otherV, outV> KTable<K, V>.outerJoin(
  other: KTable<K, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    name != null && materialized != null -> outerJoin(other, joiner, namedAs(name), materialized)
    name != null && materialized == null -> outerJoin(other, joiner, namedAs(name))
    name == null && materialized != null -> outerJoin(other, joiner, materialized)
    else                                 -> outerJoin(other, joiner)
  }
}


fun <inK, V, outK> KTable<inK, V>.toStream(
  name: String,
  mapper: KeyValueMapper<inK, V, outK>? = null,
): KStream<outK, V> = toStream(mapper, namedAs(name))


fun <K, V> KTable<K, V>.toStream(name: String): KStream<K, V> = toStream(namedAs(name))


fun <K, V> KTable<K, V>.filter(
  name: String? = null,
  materialized: Materialized<K, V, KeyValueStore<Bytes, ByteArray>>? = null,
  predicate: (K, V) -> Boolean,
): KTable<K, V> {
  return when {
    name != null && materialized != null -> filter(predicate, namedAs(name), materialized)
    name != null && materialized == null -> filter(predicate, namedAs(name))
    name == null && materialized != null -> filter(predicate, materialized)
    else                                 -> filter(predicate)
  }
}


fun <K, V> KTable<K, V>.filterNot(
  name: String? = null,
  materialized: Materialized<K, V, KeyValueStore<Bytes, ByteArray>>? = null,
  predicate: (K, V) -> Boolean,
): KTable<K, V> {
  return when {
    name != null && materialized != null -> filterNot(predicate, namedAs(name), materialized)
    name != null && materialized == null -> filterNot(predicate, namedAs(name))
    name == null && materialized != null -> filterNot(predicate, materialized)
    else                                 -> filterNot(predicate)
  }
}

//<VR> KTable<K, VR> transformValues(final ValueTransformerWithKeySupplier<? super K, ? super V, ? extends VR> transformerSupplier,final Materialized<K, VR, KeyValueStore<Bytes, byte[]>> materialized,final Named named,final String... stateStoreNames);
