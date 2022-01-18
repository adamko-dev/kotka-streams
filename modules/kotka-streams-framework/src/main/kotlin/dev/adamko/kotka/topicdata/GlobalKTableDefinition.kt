package dev.adamko.kotka.topicdata

import dev.adamko.kotka.extensions.TimestampedQueryStoreType
import dev.adamko.kotka.extensions.materializedAs
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.GlobalKTable
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.streams.state.QueryableStoreTypes.timestampedKeyValueStore

/**
 * * An update topic [topicName]
 * * plus a table-source topic [tableName]
 * * plus a state store [storeName]
 *
 * equals a GlobalTableDefinition
 *
 * After defining a [GlobalKTable], use [buildInstance] to create an instance - this has a backing
 * topic from which the GlobalKTable will be updated.
 */
abstract class GlobalKTableDefinition<K, V>(
  val tableName: String,
  val storeName: String = "$tableName-store",
  /**
   * The name of the backing topic, from which this table will be filled.
   *
   * Updates to items can be sent or received with this topic.
   */
  final override val topicName: String = "$tableName-topic",
  override val serdes: KeyValueSerdes<K, V>,
  val storeType: TimestampedQueryStoreType<K, V> = timestampedKeyValueStore(),
) : TopicDefinition<K, V> {

  val topicNames: Set<String>

  init {
    require(tableName != topicName) {
      "tableName $tableName and topicName $tableName must be different"
    }
    require(tableName != storeName) {
      "tableName $tableName and storeName $storeName must be different"
    }
    topicNames = setOf(tableName, topicName)
  }


  fun materializer(): Materialized<K, V, KeyValueStore<Bytes, ByteArray>> = materializedAs(
    storeName,
    serdes.keySerde,
    serdes.valueSerde
  )

  /**
   * A [GlobalKTable] and [the backing topic][updatesKStream] from which the table is filled.
   */
  data class Instance<K, V>(
    val updatesKStream: KStream<K, V>,
    val globalKTable: GlobalKTable<K, V>,
    val definition: GlobalKTableDefinition<K, V>,
  )

  fun buildInstance(
    builder: StreamsBuilder,
  ): Instance<K, V> {
    val updatesKStream = builder.stream(
      topicName,
      serdes.consumer("${pid}.input-stream")
    )

    updatesKStream.to(
      tableName,
      serdes.producer("${pid}.updates-to-table")
    )

    val globalKTable = builder.globalTable(
      tableName,
      serdes.consumer("${pid}.input-global-table"),
      materializer(),
    )

    return Instance(updatesKStream, globalKTable, this)
  }

}
