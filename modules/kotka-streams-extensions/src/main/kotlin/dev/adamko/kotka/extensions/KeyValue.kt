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
