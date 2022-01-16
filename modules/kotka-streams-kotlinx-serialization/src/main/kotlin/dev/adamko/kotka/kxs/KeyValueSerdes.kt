package dev.adamko.kotka.kxs

import dev.adamko.kotka.topicdata.KeyValueSerdes
import kotlinx.serialization.json.Json


inline fun <reified K, reified V> KeyValueSerdes<K, V>.kxsJson(
  jsonMapper: Json
): KeyValueSerdes<K, V> =
  KeyValueSerdes(
    jsonMapper.serde(),
    jsonMapper.serde(),
  )
