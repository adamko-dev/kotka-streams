package dev.adamko.kotka.extensions

import dev.adamko.kotka.topicdata.GlobalKTableDefinition
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.processor.StateStore

fun <Key, Val, Store : StateStore> materializedAs(
  tableDefinition: GlobalKTableDefinition<Key, Val>
): Materialized<Key, Val, Store> = materializedAs(
  tableDefinition.globalStoreName,
  tableDefinition.serdes.keySerde,
  tableDefinition.serdes.valueSerde
)
