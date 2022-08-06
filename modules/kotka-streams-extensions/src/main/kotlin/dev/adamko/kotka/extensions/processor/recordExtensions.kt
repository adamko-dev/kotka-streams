package dev.adamko.kotka.extensions.processor

import org.apache.kafka.streams.processor.api.Record


/** Get the key of a [Record] */
operator fun <K, V> Record<K, V>.component1(): K = key()

/** Get the value of a [Record] */
operator fun <K, V> Record<K, V>.component2(): V = value()

/** Get the timestamp of a [Record] */
operator fun <K, V> Record<K, V>.component3(): Long = timestamp()
