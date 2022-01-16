package dev.adamko.kotka.topicdata

import dev.adamko.kotka.extensions.namedAs
import dev.adamko.kotka.topicdata.TopicRecord.Companion.toKeyValue
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
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

/** @see KStream.map */
fun <inK, inV, outK, outV : TopicRecord<outK>> KStream<inK, inV>.mapToTopicRecords(
  name: String,
  mapper: (key: inK, value: inV) -> outV
): KStream<outK, outV> =
  map({ k, v -> mapper(k, v).toKeyValue() }, namedAs(name))


/** @see KStream.flatMap */
fun <inK, outK, inV, outV : TopicRecord<outK>> KStream<inK, inV>.flatMapToTopicRecords(
  name: String,
  mapper: (key: inK, value: inV) -> Iterable<outV>
): KStream<outK, outV> =
  flatMap(
    { k, v -> mapper(k, v).map { a: outV -> a.toKeyValue() } },
    namedAs(name)
  )
