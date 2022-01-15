package dev.adamko.kotka.topicdata;

/**
 * A definition for any topic that is Kafka.
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
