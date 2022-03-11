package dev.adamko.kotka.kxs

import dev.adamko.kotka.topicdata.KeyValueSerdes
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer


inline fun <reified T> StringFormat.kafkaSerializer(): Serializer<T> =
  Serializer { topic: String, data: T ->
    runCatching {
      encodeToString(data).encodeToByteArray()
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


inline fun <reified T> StringFormat.kafkaDeserializer(): Deserializer<T> =
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


inline fun <reified T> StringFormat.serde(): Serde<T> =
  object : Serde<T> {
    private val serializer: Serializer<T> = kafkaSerializer()
    private val deserializer: Deserializer<T> = kafkaDeserializer()
    override fun serializer(): Serializer<T> = serializer
    override fun deserializer(): Deserializer<T> = deserializer
  }


inline fun <reified K, reified V> StringFormat.keyValueSerdes(): KeyValueSerdes<K, V> =
  KeyValueSerdes(serde(), serde())
