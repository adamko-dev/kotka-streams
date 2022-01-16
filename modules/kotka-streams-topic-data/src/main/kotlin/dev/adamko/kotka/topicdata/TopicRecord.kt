package dev.adamko.kotka.topicdata

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.state.ValueAndTimestamp

interface TopicRecord<K> {

  val topicKey: K

  companion object {

    fun <K, V : TopicRecord<K>> V.toPair(): Pair<K, V> = topicKey to this
    fun <K, V : TopicRecord<K>> V.toKeyValue(): KeyValue<K, V> = KeyValue.pair(topicKey, this)

    fun <V : TopicRecord<*>> V.toTimestampValue(timestamp: Long): ValueAndTimestamp<V> =
      ValueAndTimestamp.make(this, timestamp)

    operator fun <K, T : TopicRecord<K>> T.component1(): K = topicKey
    operator fun <K, T : TopicRecord<K>> T.component2(): T = this

  }
}
