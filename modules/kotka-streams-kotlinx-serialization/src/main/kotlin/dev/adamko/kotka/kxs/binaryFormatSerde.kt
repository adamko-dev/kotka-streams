package dev.adamko.kotka.kxs

import dev.adamko.kotka.topicdata.KeyValueSerdes
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer


inline fun <reified T> BinaryFormat.kafkaSerializer() =
  Serializer { topic: String, data: T ->
    runCatching {
      encodeToByteArray(data)
    }.getOrElse { e ->
      println(
        """
          Exception on encodeToByteArray 
          Topic: $topic
          topicData: $data
          topicData as T: $data
        """.trimIndent()
      )
      e.printStackTrace()
      throw e
    }
  }

inline fun <reified T> BinaryFormat.kafkaDeserializer() =
  Deserializer { topic: String, data: ByteArray ->
    runCatching {
      decodeFromByteArray<T>(data)
    }.getOrElse { e ->
      println(
        """
          Exception on decodeFromByteArray,    
          Topic: $topic
          topicData: $data
          topicData as T: ${data as? T}
        """.trimIndent()
      )
      e.printStackTrace()
      throw e
    }
  }


inline fun <reified T> BinaryFormat.serde() = object : Serde<T> {
  override fun serializer(): Serializer<T> = kafkaSerializer()
  override fun deserializer(): Deserializer<T> = kafkaDeserializer()
}

inline fun <reified K, reified V> BinaryFormat.keyValueSerdes(): KeyValueSerdes<K, V> =
  KeyValueSerdes(serde(), serde())
