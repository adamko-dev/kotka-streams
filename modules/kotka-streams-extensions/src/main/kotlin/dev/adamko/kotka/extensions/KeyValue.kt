package dev.adamko.kotka.extensions

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.state.ValueAndTimestamp


/** Convert a [Pair] to a [KeyValue] */
inline fun <reified K, reified V> Pair<K, V>.toKeyValue(): KeyValue<K, V> =
  KeyValue.pair(first, second)

//infix fun <K, V> K.kvPair(value: V): KeyValue<K, V> = KeyValue(this, value)

//fun <V> valueAndTimestamp(value: V, timestamp: Long): ValueAndTimestamp<V> =
//  ValueAndTimestamp.make(value, timestamp)


/** Get the key of a [KeyValue] */
@JvmName("keyValueGetKey")
operator fun <K, V> KeyValue<K, V>.component1(): K = key
/** Get the value of a [KeyValue] */
@JvmName("keyValueGetValue")
operator fun <K, V> KeyValue<K, V>.component2(): V = value


/** Convert a [Triple] to a [KeyValue], with [a timestamped value][ValueAndTimestamp] */
inline fun <reified K, reified V> Triple<K, V, Long>.toKeyValueTimestamp(): KeyValue<K, ValueAndTimestamp<V>> =
  KeyValue.pair(first, ValueAndTimestamp.make(second, third))


/** Get the [timestamp][ValueAndTimestamp.timestamp] of a timestamped-[KeyValue] */
val <K, V> KeyValue<K, ValueAndTimestamp<V>>.timestamp: Long
  get() = value.timestamp()


/** Get the key of a timestamped-[KeyValue] */
@JvmName("keyValueTimestampGetKey")
operator fun <K, V> KeyValue<K, ValueAndTimestamp<V>>.component1(): K = key
/** Get the value of a timestamped-[KeyValue] */
@JvmName("keyValueTimestampGetValue")
operator fun <K, V> KeyValue<K, ValueAndTimestamp<V>>.component2(): V = value.value()
/** Get the timestamp of a timestamped-[KeyValue] */
@JvmName("keyValueTimestampGetTimestamp")
operator fun <K, V> KeyValue<K, ValueAndTimestamp<V>>.component3(): Long = value.timestamp()


/** Get the value of a [ValueAndTimestamp] */
@JvmName("valueTimestampGetValue")
operator fun <V> ValueAndTimestamp<V>.component1(): V = value()
/** Get the timestamp of a [ValueAndTimestamp] */
@JvmName("valueTimestampGetTimestamp")
operator fun <V> ValueAndTimestamp<V>.component2(): Long = timestamp()
