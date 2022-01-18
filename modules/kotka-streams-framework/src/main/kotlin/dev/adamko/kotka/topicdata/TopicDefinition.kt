package dev.adamko.kotka.topicdata;

import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable


/**
 * A definition for any Kafka topic.
 *
 * These can be used to create
 * [KTopic][org.apache.kafka.streams.kstream.KStream]s,
 * [KTables][org.apache.kafka.streams.kstream.KTable]s,
 * or [GlobalKTables][org.apache.kafka.streams.kstream.GlobalKTable]s.
 */
interface TopicDefinition<K, V> {

  val topicName: String

  val serdes: KeyValueSerdes<K, V>

  /** unique Kafka processor ID - to help ensure topology names are unique */
  val pid: String
    get() = this::class.simpleName!!

}


fun <K, V> TopicDefinition<K, V>.consumeAsKStream(
  builder: StreamsBuilder,
  consumer: Consumed<K, V> = serdes.consumer("${pid}.input-stream")
): KStream<K, V> {
  return builder.stream(
    topicName,
    consumer,
  )
}


fun <K, V> TopicDefinition<K, V>.consumeAsKTable(
  builder: StreamsBuilder,
  consumer: Consumed<K, V> = serdes.consumer("${pid}.input-table")
): KTable<K, V> {
  return builder.table(
    topicName,
    consumer,
  )
}
