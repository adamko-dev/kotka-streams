package dev.adamko.kotka.kxs

import kotlinx.serialization.StringFormat
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Joined
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.Repartitioned
import org.apache.kafka.streams.processor.StateStore


class KotkaSerdeModule(
  val mapper: StringFormat
) {


  /** @see Materialized */
  inline fun <reified Key, reified Val, Store : StateStore> materializedAs(
    name: String,
    keySerde: Serde<Key> = mapper.serde(),
    valueSerde: Serde<Val> = mapper.serde(),
  ): Materialized<Key, Val, Store> =
    Materialized.`as`<Key, Val, Store>(name)
      .withKeySerde(keySerde)
      .withValueSerde(valueSerde)


  /** @see Repartitioned */
  inline fun <reified Key, reified Val> repartitionedAs(
    name: String,
    keySerde: Serde<Key> = mapper.serde(),
    valueSerde: Serde<Val> = mapper.serde(),
  ): Repartitioned<Key, Val> =
    Repartitioned.`as`<Key, Val>(name)
      .withKeySerde(keySerde)
      .withValueSerde(valueSerde)


  /** @see Produced */
  inline fun <reified Key, reified Val> producedAs(
    name: String,
    keySerde: Serde<Key> = mapper.serde(),
    valueSerde: Serde<Val> = mapper.serde(),
  ): Produced<Key, Val> =
    Produced.`as`<Key, Val>(name)
      .withKeySerde(keySerde)
      .withValueSerde(valueSerde)


  /** @see Joined */
  inline fun <reified Key, reified Val, reified V_other> joinedAs(
    name: String,
    keySerde: Serde<Key> = mapper.serde(),
    valueSerde: Serde<Val> = mapper.serde(),
    otherValueSerde: Serde<V_other>? = mapper.serde(),
  ): Joined<Key, Val, V_other> =
    Joined.`as`<Key, Val, V_other>(name)
      .withKeySerde(keySerde)
      .withValueSerde(valueSerde)
      .withOtherValueSerde(otherValueSerde)


  /** See [Grouped.as] */
  inline fun <reified Key, reified Val> groupedAs(
    name: String,
    keySerde: Serde<Key>? = mapper.serde(),
    valueSerde: Serde<Val>? = mapper.serde(),
  ): Grouped<Key, Val> =
    Grouped.`as`<Key, Val>(name)
      .withKeySerde(keySerde)
      .withValueSerde(valueSerde)

}
