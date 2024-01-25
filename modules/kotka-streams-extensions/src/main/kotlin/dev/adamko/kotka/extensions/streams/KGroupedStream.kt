package dev.adamko.kotka.extensions.streams

import dev.adamko.kotka.extensions.namedAs
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.Aggregator
import org.apache.kafka.streams.kstream.Initializer
import org.apache.kafka.streams.kstream.KGroupedStream
import org.apache.kafka.streams.kstream.KTable
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Reducer
import org.apache.kafka.streams.state.KeyValueStore


/** @see KGroupedStream.count */
fun <K> KGroupedStream<K, *>.count(
  name: String? = null,
  materialized: Materialized<K, Long, KeyValueStore<Bytes, ByteArray>>? = null,
): KTable<K, Long> {
  return when {
    name != null && materialized != null -> count(namedAs(name), materialized)
    name == null && materialized != null -> count(materialized)
    name != null && materialized == null -> count(namedAs(name))
    else                                 -> count()
  }
}


// note: 'aggregate' and 'reduce' each have two extensions rather than one with
// default values. This is so the 'name' param will be the first param - I
// don't think it's as natural otherwise if it's not first, or a named
// parameter is required.


/** @see KGroupedStream.aggregate */
fun <K, inV, outV> KGroupedStream<K, inV>.aggregate(
  initializer: Initializer<outV>,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>,
  aggregator: Aggregator<K, inV, outV>,
): KTable<K, outV> = aggregate(initializer, aggregator, materialized)


/** @see KGroupedStream.aggregate */
fun <K, inV, outV> KGroupedStream<K, inV>.aggregate(
  name: String,
  materialized: Materialized<K, outV, KeyValueStore<Bytes, ByteArray>>,
  initializer: Initializer<outV>,
  aggregator: Aggregator<K, inV, outV>,
): KTable<K, outV> = aggregate(initializer, aggregator, namedAs(name), materialized)


/** @see KGroupedStream.reduce */
fun <K, V> KGroupedStream<K, V>.reduce(
  materialized: Materialized<K, V, KeyValueStore<Bytes, ByteArray>>,
  reducer: Reducer<V>,
): KTable<K, V> = reduce(reducer, materialized)


/** @see KGroupedStream.reduce */
fun <K, V> KGroupedStream<K, V>.reduce(
  name: String,
  materialized: Materialized<K, V, KeyValueStore<Bytes, ByteArray>>,
  reducer: Reducer<V>,
): KTable<K, V> = reduce(reducer, namedAs(name), materialized)


//<VOut> CogroupedKStream<K, VOut> cogroup(final Aggregator<? super K, ? super V, VOut> aggregator);
//<W extends Window> TimeWindowedKStream<K, V> windowedBy(final Windows<W> windows);
//SessionWindowedKStream<K, V> windowedBy(final SessionWindows windows);
//TimeWindowedKStream<K, V> windowedBy(final SlidingWindows windows);
