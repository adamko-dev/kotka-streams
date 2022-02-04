package dev.adamko.kotka.extensions.processor

import org.apache.kafka.streams.processor.api.Record

operator fun <K, V> Record<K, V>.component1(): K = key()
operator fun <K, V> Record<K, V>.component2(): V = value()
operator fun <K, V> Record<K, V>.component3(): Long = timestamp()
