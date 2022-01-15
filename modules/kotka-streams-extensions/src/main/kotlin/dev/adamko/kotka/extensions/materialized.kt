package dev.adamko.kotka.extensions

import kotlin.time.Duration
import kotlin.time.toJavaDuration
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.processor.StateStore
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.streams.state.SessionBytesStoreSupplier
import org.apache.kafka.streams.state.SessionStore
import org.apache.kafka.streams.state.WindowBytesStoreSupplier
import org.apache.kafka.streams.state.WindowStore


/** @see Materialized */
fun <Key, Val, Store : StateStore> materializedAs(
  storeName: String,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  loggingConfig: Map<String, String>? = null,
  cachingEnabled: Boolean = true,
  retention: Duration? = null,
): Materialized<Key, Val, Store> {
  val materialized = Materialized.`as`<Key, Val, Store>(storeName)

  return materialized.withConfig(
    keySerde,
    valueSerde,
    loggingConfig,
    cachingEnabled,
    retention
  )
}


/** @see Materialized */
fun <Key, Val> materializedAs(
  storeSupplier: WindowBytesStoreSupplier,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  loggingConfig: Map<String, String>? = null,
  cachingEnabled: Boolean = true,
  retention: Duration? = null,
): Materialized<Key, Val, WindowStore<Bytes, ByteArray>> {

  val materialized: Materialized<Key, Val, WindowStore<Bytes, ByteArray>> =
    Materialized.`as`(storeSupplier)

  return materialized.withConfig(
    keySerde,
    valueSerde,
    loggingConfig,
    cachingEnabled,
    retention
  )
}


/** @see Materialized */
fun <Key, Val> materializedAs(
  storeSupplier: SessionBytesStoreSupplier,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  loggingConfig: Map<String, String>? = null,
  cachingEnabled: Boolean = true,
  retention: Duration? = null,
): Materialized<Key, Val, SessionStore<Bytes, ByteArray>> {

  val materialized: Materialized<Key, Val, SessionStore<Bytes, ByteArray>> =
    Materialized.`as`(storeSupplier)

  return materialized.withConfig(
    keySerde,
    valueSerde,
    loggingConfig,
    cachingEnabled,
    retention
  )
}


/** @see Materialized */
fun <Key, Val> materializedAs(
  storeSupplier: KeyValueBytesStoreSupplier,
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  loggingConfig: Map<String, String>? = null,
  cachingEnabled: Boolean = true,
  retention: Duration? = null,
): Materialized<Key, Val, KeyValueStore<Bytes, ByteArray>> {

  val materialized: Materialized<Key, Val, KeyValueStore<Bytes, ByteArray>> =
    Materialized.`as`(storeSupplier)

  return materialized.withConfig(
    keySerde,
    valueSerde,
    loggingConfig,
    cachingEnabled,
    retention
  )
}


/** @see Materialized */
fun <Key, Val, Store : StateStore> Materialized<Key, Val, Store>.withConfig(
  keySerde: Serde<Key>? = null,
  valueSerde: Serde<Val>? = null,
  loggingConfig: Map<String, String>? = null,
  cachingEnabled: Boolean = true,
  retention: Duration? = null,
): Materialized<Key, Val, Store> {
  var materialized: Materialized<Key, Val, Store> = this
    .withKeySerde(keySerde)
    .withValueSerde(valueSerde)

  if (retention != null)
    materialized = materialized.withRetention(retention.toJavaDuration())

  if (loggingConfig != null)
    materialized = materialized.withLoggingEnabled(loggingConfig)

  materialized = when (cachingEnabled) {
    true  -> materialized.withCachingEnabled()
    false -> materialized.withCachingDisabled()
  }

  return materialized
}
