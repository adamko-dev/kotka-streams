package dev.adamko.kotka.extensions.streams

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.processor.RecordContext


fun interface TopicNameExtractorKt<K, V> {
  fun Context.extract(keyValue: KeyValue<K, V>): String

  interface Context {
    val recordContext: RecordContext
  }
}


@JvmInline
internal value class TopicNameExtractorContextInternal(
  override val recordContext: RecordContext
) : TopicNameExtractorKt.Context
