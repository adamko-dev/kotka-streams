package dev.adamko.kotka.extensions.processor

import org.apache.kafka.streams.processor.api.Record

private operator fun <K, V> Record<K, V>.component1(): K = key()
private operator fun <K, V> Record<K, V>.component2(): V = value()
private operator fun <K, V> Record<K, V>.component3(): Long = timestamp()
