package dev.adamko.kotka.extensions

import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream


/** @see org.apache.kafka.streams.StreamsBuilder.stream */
fun <K, V> StreamsBuilder.stream(
  consumed: Consumed<K, V>? = null,
  vararg topics: String,
): KStream<K, V> = when (consumed) {
  null -> stream(topics.toList())
  else -> stream(topics.toList(), consumed)
}


/** @see org.apache.kafka.streams.StreamsBuilder.stream */
fun <K, V> StreamsBuilder.stream(
  vararg topics: String,
): KStream<K, V> = stream(topics.toList())
