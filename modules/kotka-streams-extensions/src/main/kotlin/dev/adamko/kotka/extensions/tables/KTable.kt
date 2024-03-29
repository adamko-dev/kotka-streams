package dev.adamko.kotka.extensions.tables

import dev.adamko.kotka.extensions.namedAs
import dev.adamko.kotka.extensions.tableJoined
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.*
import org.apache.kafka.streams.state.KeyValueStore


/** @see org.apache.kafka.streams.kstream.KTable.mapValues */
fun <K, inV, outV> KTable<K, inV>.mapValues(
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  mapper: (readOnlyKey: K, value: inV) -> outV,
): KTable<K, outV> =
  when {
    name != null && materialized != null -> mapValues(mapper, namedAs(name), materialized)
    name != null && materialized == null -> mapValues(mapper, namedAs(name))
    name == null && materialized != null -> mapValues(mapper, materialized)
    else                                 -> mapValues(mapper)
  }


/** @see org.apache.kafka.streams.kstream.KTable.groupBy */
fun <inK, inV, outK, outV> KTable<inK, inV>.groupBy(
  grouped: Grouped<outK, outV>? = null,
  selector: KeyValueMapper<inK, inV, KeyValue<outK, outV>>
): KGroupedTable<outK, outV> = when (grouped) {
  null -> groupBy(selector)
  else -> groupBy(selector, grouped)
}


/** @see org.apache.kafka.streams.kstream.KTable.join */
fun <K, V, otherV, outV> KTable<K, V>.join(
  other: KTable<K, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> =
  when {
    name != null && materialized != null -> join(other, joiner, namedAs(name), materialized)
    name != null && materialized == null -> join(other, joiner, namedAs(name))
    name == null && materialized != null -> join(other, joiner, materialized)
    else                                 -> join(other, joiner)
  }

/**
 * A function that extracts the key ([otherK]) from this table's value ([V]).
 * If the result is null, the update is ignored as invalid.
 * See [KTable.join]
 */
fun interface ForeignKeyExtractor<V, otherK> : (V) -> otherK?


/** @see org.apache.kafka.streams.kstream.KTable.join */

fun <K, V, otherK, otherV, outV> KTable<K, V>.join(
  other: KTable<otherK, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  foreignKeyExtractor: ForeignKeyExtractor<V, otherK>,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> =
  join(
    other = other,
    tableJoined = if (name == null) null else tableJoined(name),
    materialized = materialized,
    foreignKeyExtractor = foreignKeyExtractor,
    joiner = joiner,
  )


/** @see org.apache.kafka.streams.kstream.KTable.join */
fun <K, V, otherK, otherV, outV> KTable<K, V>.join(
  other: KTable<otherK, otherV>,
  tableJoined: TableJoined<K, otherK>? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  foreignKeyExtractor: ForeignKeyExtractor<V, otherK>,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    tableJoined != null && materialized != null ->
      join(other, foreignKeyExtractor, joiner, tableJoined, materialized)

    tableJoined != null && materialized == null ->
      join(other, foreignKeyExtractor, joiner, tableJoined)

    tableJoined == null && materialized != null ->
      join(other, foreignKeyExtractor, joiner, materialized)

    else                                        ->
      join(other, foreignKeyExtractor, joiner)
  }
}


/** @see org.apache.kafka.streams.kstream.KTable.leftJoin */
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


/** @see org.apache.kafka.streams.kstream.KTable.leftJoin */
fun <K, V, otherK, otherV, outV> KTable<K, V>.leftJoin(
  other: KTable<otherK, otherV>,
  name: String? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  foreignKeyExtractor: (V) -> otherK,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> =
  leftJoin(
    other = other,
    tableJoined = if (name == null) null else tableJoined(name),
    materialized = materialized,
    foreignKeyExtractor = foreignKeyExtractor,
    joiner = joiner,
  )


/** @see org.apache.kafka.streams.kstream.KTable.leftJoin */
fun <K, V, otherK, otherV, outV> KTable<K, V>.leftJoin(
  other: KTable<otherK, otherV>,
  tableJoined: TableJoined<K, otherK>? = null,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>? = null,
  foreignKeyExtractor: (V) -> otherK,
  joiner: ValueJoiner<V, otherV, outV>,
): KTable<K, outV> {
  return when {
    tableJoined != null && materialized != null ->
      leftJoin(other, foreignKeyExtractor, joiner, tableJoined, materialized)

    tableJoined != null && materialized == null ->
      leftJoin(other, foreignKeyExtractor, joiner, tableJoined)

    tableJoined == null && materialized != null ->
      leftJoin(other, foreignKeyExtractor, joiner, materialized)

    else                                        ->
      leftJoin(other, foreignKeyExtractor, joiner)
  }
}


/** @see org.apache.kafka.streams.kstream.KTable.outerJoin */
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


// the value of the resulting KStream is nullable, because it includes record deletions.
/** @see org.apache.kafka.streams.kstream.KTable.toStream */
fun <inK, V, outK> KTable<inK, V>.toStream(
  name: String,
  mapper: KeyValueMapper<inK, V, outK>? = null,
): KStream<outK, V?> = toStream(mapper, namedAs(name))


// the value of the resulting KStream is nullable, because it includes record deletions.
/** @see org.apache.kafka.streams.kstream.KTable.toStream */
fun <K, V> KTable<K, V>.toStream(name: String): KStream<K, V?> = toStream(namedAs(name))


/** @see org.apache.kafka.streams.kstream.KTable.filter */
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

/** @see org.apache.kafka.streams.kstream.KTable.filterNot */
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
