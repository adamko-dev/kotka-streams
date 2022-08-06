package dev.adamko.kotka.extensions

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.state.ValueAndTimestamp


inline fun <reified K, reified V> Pair<K, V>.toKeyValue(): KeyValue<K, V> =
  KeyValue.pair(first, second)

//infix fun <K, V> K.kvPair(value: V): KeyValue<K, V> = KeyValue(this, value)

//fun <V> valueAndTimestamp(value: V, timestamp: Long): ValueAndTimestamp<V> =
//  ValueAndTimestamp.make(value, timestamp)


@JvmName("keyValueGetKey")
operator fun <K, V> KeyValue<K, V>.component1(): K = key
@JvmName("keyValueGetValue")
operator fun <K, V> KeyValue<K, V>.component2(): V = value


inline fun <reified K, reified V> Triple<K, V, Long>.toKeyValueTimestamp(): KeyValue<K, ValueAndTimestamp<V>> =
  KeyValue.pair(first, ValueAndTimestamp.make(second, third))


val <K, V> KeyValue<K, ValueAndTimestamp<V>>.timestamp: Long
  get() = value.timestamp()


@JvmName("keyValueTimestampGetKey")
operator fun <K, V> KeyValue<K, ValueAndTimestamp<V>>.component1(): K = key
@JvmName("keyValueTimestampGetValue")
operator fun <K, V> KeyValue<K, ValueAndTimestamp<V>>.component2(): V = value.value()
@JvmName("keyValueTimestampGetTimestamp")
operator fun <K, V> KeyValue<K, ValueAndTimestamp<V>>.component3(): Long = value.timestamp()


@JvmName("valueTimestampGetValue")
operator fun <V> ValueAndTimestamp<V>.component1(): V = value()
@JvmName("valueTimestampGetTimestamp")
operator fun <V> ValueAndTimestamp<V>.component2(): Long = timestamp()
