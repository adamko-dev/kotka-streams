package dev.adamko.kotka.kxs

import dev.adamko.kotka.topicdata.KeyValueSerdes
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer


inline fun <reified T> BinaryFormat.kafkaSerializer(
  serializer: KSerializer<T> = serializersModule.serializer()
): Serializer<T> =
  Serializer { topic: String, data: T ->
    runCatching {
      encodeToByteArray(serializer, data)
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


inline fun <reified T> BinaryFormat.kafkaDeserializer(
  serializer: KSerializer<T> = serializersModule.serializer()
): Deserializer<T> =
  Deserializer { topic: String, data: ByteArray ->
    runCatching {
      decodeFromByteArray(serializer, data)
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


inline fun <reified T> BinaryFormat.serde(
  serializer: KSerializer<T> = serializersModule.serializer()
): Serde<T> =
  object : Serde<T> {
    private val kafkaSerializer: Serializer<T> = kafkaSerializer(serializer)
    private val kafkaDeserializer: Deserializer<T> = kafkaDeserializer(serializer)
    override fun serializer(): Serializer<T> = kafkaSerializer
    override fun deserializer(): Deserializer<T> = kafkaDeserializer

    override fun toString(): String = "BinaryFormat serde: $serializer"
  }


inline fun <reified K, reified V> BinaryFormat.keyValueSerdes(
  keySerializer: KSerializer<K> = serializersModule.serializer(),
  valueSerializer: KSerializer<V> = serializersModule.serializer(),
): KeyValueSerdes<K, V> =
  KeyValueSerdes(
    keySerde = serde(keySerializer),
    valueSerde = serde(valueSerializer),
  )
