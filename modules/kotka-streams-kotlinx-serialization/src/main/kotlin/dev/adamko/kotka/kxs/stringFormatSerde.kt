package dev.adamko.kotka.kxs

import dev.adamko.kotka.topicdata.KeyValueSerdes
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer


inline fun <reified T> StringFormat.kafkaSerializer() =
  Serializer { topic: String, data: T? ->
    runCatching {
      encodeToString(data).encodeToByteArray()
//      jsonMapper.encodeToString(data as T).encodeToByteArray()
    }.getOrElse { e ->
      println(
        """
          Exception on encodeToString 
          Topic: $topic
          topicData: $data
          topicData as T: $data
        """.trimIndent()
      )
      e.printStackTrace()
      throw e
    }
  }

inline fun <reified T> StringFormat.kafkaDeserializer() =
  Deserializer { topic: String, data: ByteArray ->
    runCatching {
      decodeFromString<T>(data.decodeToString())
    }.getOrElse { e ->
      println(
        """
          Exception on decodeFromString,    
          Topic: $topic
          topicData: $data
          topicData as T: ${data as? T}
        """.trimIndent()
      )
      e.printStackTrace()
      throw e
    }
  }

inline fun <reified T> StringFormat.serde() = object : Serde<T> {
  override fun serializer(): Serializer<T?> = kafkaSerializer()
  override fun deserializer(): Deserializer<T> = kafkaDeserializer()
}


inline fun <reified K, reified V> StringFormat.keyValueSerdes(): KeyValueSerdes<K, V> =
  KeyValueSerdes(serde(), serde())
