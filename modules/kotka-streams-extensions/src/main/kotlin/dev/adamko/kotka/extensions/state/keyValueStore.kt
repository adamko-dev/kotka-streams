package dev.adamko.kotka.extensions.state

import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.state.KeyValueIterator
import org.apache.kafka.streams.state.KeyValueStore


typealias KeyValueIteratorOperation<K, V, Result> = KeyValueIterator<K, V>.() -> Result


/**
 * Iterate over [all][KeyValueStore.all] records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.all
 */
inline fun <K, V, Result> KeyValueStore<K, V>.useAll(
  operation: KeyValueIteratorOperation<K, V, Result>
): Result =
  all().use { iterator -> iterator.operation() }


/**
 * Reverse-iterate over [all][KeyValueStore.reverseAll] records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.reverseAll
 */
inline fun <K, V, Result> KeyValueStore<K, V>.useReverseAll(
  operation: KeyValueIteratorOperation<K, V, Result>
): Result =
  reverseAll().use { iterator -> iterator.operation() }


/**
 * Iterate over [a range][KeyValueStore.range] of records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.range
 */
inline fun <K, V, Result> KeyValueStore<K, V>.useRange(
  from: K? = null,
  to: K? = null,
  operation: KeyValueIteratorOperation<K, V, Result>,
): Result =
  range(from, to).use { iterator -> iterator.operation() }


/**
 * Reverse-iterate over [a range][KeyValueStore.range] of records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.reverseRange
 */
inline fun <K, V, Result> KeyValueStore<K, V>.useReverseRange(
  from: K? = null,
  to: K? = null,
  operation: KeyValueIteratorOperation<K, V, Result>,
): Result =
  reverseRange(from, to).use { iterator -> iterator.operation() }


/**
 * Iterate over records based on the key prefix.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.prefixScan
 */
inline fun <K, V, Prefix, Result> KeyValueStore<K, V>.usePrefixScan(
  prefix: Prefix,
  prefixKeySerializer: Serializer<Prefix>,
  operation: KeyValueIteratorOperation<K, V, Result>,
): Result =
  prefixScan(prefix, prefixKeySerializer).use { iterator -> iterator.operation() }


/**
 * Generate a [Sequence] of [all][KeyValueStore.all] records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.all
 */
fun <K, V> KeyValueStore<K, V>.allAsSequence(): Sequence<KeyValue<K, V>> =
  all().use { it.asSequence() }


/**
 * Generate a [Sequence] of all records, in [reverse][KeyValueStore.reverseAll]
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.reverseAll
 */
fun <K, V> KeyValueStore<K, V>.reverseAllAsSequence(): Sequence<KeyValue<K, V>> =
  reverseAll().use { it.asSequence() }


/**
 * Generate a [Sequence] of [a range][KeyValueStore.range] of records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.range
 */
fun <K, V> KeyValueStore<K, V>.rangeAsSequence(
  from: K? = null,
  to: K? = null,
): Sequence<KeyValue<K, V>> =
  range(from, to).use { it.asSequence() }


/**
 * Generate a [Sequence] of [a reverse-range][KeyValueStore.range] of records.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.reverseRange
 */
fun <K, V> KeyValueStore<K, V>.reverseRangeAsSequence(
  from: K? = null,
  to: K? = null,
): Sequence<KeyValue<K, V>> =
  reverseRange(from, to).use { it.asSequence() }


/**
 * Iterate over keys based on the key prefix.
 *
 * This function is useful because the [KeyValueIterator] will be closed automatically
 * (see: [kotlin.io.use]) after all values have been consumed.
 *
 * @see KeyValueStore.prefixScan
 */
fun <K, V, Prefix> KeyValueStore<K, V>.prefixScanAsSequence(
  prefix: Prefix,
  prefixKeySerializer: Serializer<Prefix>,
): Sequence<KeyValue<K, V>> =
  prefixScan(prefix, prefixKeySerializer).use { it.asSequence() }
