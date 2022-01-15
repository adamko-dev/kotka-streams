package dev.adamko.kotka.extensions

import org.apache.kafka.streams.kstream.Branched
import org.apache.kafka.streams.kstream.BranchedKStream
import org.apache.kafka.streams.kstream.Predicate


fun <K, V> BranchedKStream<K, V>.branch(
  branched: Branched<K, V>,
  predicate: Predicate<K, V>,
): BranchedKStream<K, V> = branch(predicate, branched)
