package dev.adamko.kotka.extensions

import dev.adamko.kotka.topicdata.TopicRecord
import dev.adamko.kotka.topicdata.TopicRecord.Companion.toKeyValue
import org.apache.kafka.streams.kstream.KStream


/** @see KStream.map */
inline fun <inK, inV, reified outK, reified outV : TopicRecord<outK>> KStream<inK, inV>.mapTopicRecords(
  name: String,
  crossinline mapper: (key: inK, value: inV) -> outV
): KStream<outK, outV> =
  map({ k, v -> mapper(k, v).toKeyValue() }, namedAs(name))


/** @see KStream.flatMap */
inline fun <inK, reified outK, inV, reified outV : TopicRecord<outK>> KStream<inK, inV>.flatMapTopicRecords(
  name: String,
  crossinline mapper: (key: inK, value: inV) -> Iterable<outV>
): KStream<outK, outV> =
  flatMap(
    { k, v -> mapper(k, v).map { a: outV -> a.toKeyValue() } },
    namedAs(name)
  )
