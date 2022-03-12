package dev.adamko.kotka.extensions.streams

import dev.adamko.kotka.extensions.namedAs
import dev.adamko.kotka.extensions.toKeyValue
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.BranchedKStream
import org.apache.kafka.streams.kstream.ForeachAction
import org.apache.kafka.streams.kstream.GlobalKTable
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Joined
import org.apache.kafka.streams.kstream.KGroupedStream
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable
import org.apache.kafka.streams.kstream.KeyValueMapper
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.ValueJoinerWithKey
import org.apache.kafka.streams.processor.TopicNameExtractor
import org.apache.kafka.streams.state.KeyValueStore


/** @see KStream.map */
inline fun <inK, inV, reified outK, reified outV> KStream<inK, inV>.map(
  name: String,
  crossinline mapper: (key: inK, value: inV) -> Pair<outK, outV>
): KStream<outK, outV> =
  map({ k, v -> mapper(k, v).toKeyValue() }, namedAs(name))


/** @see [KStream.mapValues] */
fun <K, inV, outV> KStream<K, inV>.mapValues(
  name: String,
  mapper: (key: K, value: inV) -> outV
): KStream<K, outV> = mapValues(mapper, namedAs(name))


/** @see KStream.flatMap */
inline fun <inK, inV, reified outK, reified outV> KStream<inK, inV>.flatMap(
  name: String? = null,
  crossinline mapper: (key: inK, value: inV) -> Iterable<Pair<outK, outV>>
): KStream<outK, outV> {
  return when (name) {
    null -> flatMap { k, v -> mapper(k, v).map { it.toKeyValue() } }
    else -> flatMap({ k, v -> mapper(k, v).map { it.toKeyValue() } }, namedAs(name))
  }
}


/** @see KStream.flatMapValues */
inline fun <inK, inV, reified outV> KStream<inK, inV>.flatMapValues(
  name: String? = null,
  crossinline mapper: (key: inK, value: inV) -> Iterable<outV>
): KStream<inK, outV> {
  return when (name) {
    null -> flatMapValues { k, v -> mapper(k, v) }
    else -> flatMapValues({ k, v -> mapper(k, v) }, namedAs(name))
  }
}


/** @see KStream.groupBy */
fun <K, V, outK> KStream<K, V>.groupBy(
  grouped: Grouped<outK, V>,
  keySelector: (K, V) -> outK
): KGroupedStream<outK, V> = groupBy(keySelector, grouped)


fun <K, V> KStream<K, V>.to(
  produced: Produced<K, V>,
  topicNameExtractor: TopicNameExtractor<K, V>,
) = to(topicNameExtractor, produced)


fun <K, V> KStream<K, V>.filter(
  name: String,
  predicate: (K, V) -> Boolean,
): KStream<K, V> = filter(predicate, namedAs(name))


fun <K, V> KStream<K, V>.filterNot(
  name: String,
  predicate: (K, V) -> Boolean,
): KStream<K, V> = filterNot(predicate, namedAs(name))


fun <K, V> KStream<K, V>.merge(
  name: String,
  other: KStream<K, V>,
): KStream<K, V> = merge(other, namedAs(name))


fun <K, V> KStream<K, V>.toTable(
  name: String? = null,
  materialized: Materialized<K, V, KeyValueStore<Bytes, ByteArray>>? = null
): KTable<K, V> {
  return when {
    name != null && materialized != null -> toTable(namedAs(name), materialized)
    name == null && materialized != null -> toTable(materialized)
    name != null && materialized == null -> toTable(namedAs(name))
    else                                 -> toTable()
  }
}


fun <K, V> KStream<K, V>.split(
  name: String? = null
): BranchedKStream<K, V> =
  when (name) {
    null -> split()
    else -> split(namedAs(name))
  }


fun <K, inV, otherK, otherV, outV> KStream<K, inV>.join(
  table: KTable<otherK, otherV>,
  valueJoiner: ValueJoinerWithKey<K, inV, otherV?, outV>,
  joined: Joined<K, inV, outV>,
): KStream<K, outV> {
  return join(
    table,
    valueJoiner,
    joined,
  )
}


fun <K, inV, otherK, otherV, outV> KStream<K, inV>.join(
  name: String,
  globalTable: GlobalKTable<otherK, otherV>,
  keySelector: KeyValueMapper<K, inV, otherK?>,
  valueJoiner: ValueJoinerWithKey<K, inV, otherV?, outV>,
): KStream<K, outV> {
  return join(
    globalTable,
    keySelector,
    valueJoiner,
    namedAs(name),
  )
}


fun <K, inV, otherK, otherV, outV> KStream<K, inV>.leftJoin(
  name: String,
  globalTable: GlobalKTable<otherK, otherV>,
  keySelector: KeyValueMapper<K, inV, otherK?>,
  valueJoiner: ValueJoinerWithKey<K, inV, otherV?, outV>,
): KStream<K, outV> {
  return leftJoin(
    globalTable,
    keySelector,
    valueJoiner,
    namedAs(name),
  )
}


/** @see KStream.foreach */
fun <K, V> KStream<K, V>.forEach(
  name: String? = null,
  forEachAction: ForeachAction<K, V>,
): Unit = when (name) {
  null -> foreach(forEachAction)
  else -> foreach(forEachAction, namedAs(name))
}


/** @see KStream.peek */
fun <K, V> KStream<K, V>.peek(
  name: String? = null,
  forEachAction: ForeachAction<K, V>,
): KStream<K, V> = when (name) {
  null -> peek(forEachAction)
  else -> peek(forEachAction, namedAs(name))
}


//
//  fun <K1, V1> flatTransform(
//    transformerSupplier: TransformerSupplier<in K?, in V?, Iterable<KeyValue<K1, V1>?>?>?,
//    named: Named?,
//    vararg stateStoreNames: String?
//  ): KStream<K1, V1>?
//
//  fun <K1, V1> flatTransform(
//    transformerSupplier: TransformerSupplier<in K?, in V?, Iterable<KeyValue<K1, V1>?>?>?,
//    vararg stateStoreNames: String?
//  ): KStream<K1, V1>?
//
//  fun <K1, V1> transform(
//    transformerSupplier: TransformerSupplier<in K?, in V?, KeyValue<K1, V1>?>?,
//    named: Named?,
//    vararg stateStoreNames: String?
//  ): KStream<K1, V1>?
//
//  fun <KR, VR> map(
//    mapper: KeyValueMapper<in K?, in V?, out KeyValue<out KR, out VR>?>?,
//    named: Named?
//  ): KStream<KR, VR>?
//
//  fun <KR> selectKey(mapper: KeyValueMapper<in K?, in V?, out KR>?, named: Named?): KStream<KR, V?>?
//  fun <VO, VR> join(
//    otherStream: KStream<K?, VO>?,
//    joiner: ValueJoinerWithKey<in K?, in V?, in VO, out VR>?,
//    windows: JoinWindows?,
//    streamJoined: StreamJoined<K?, V?, VO>?
//  ): KStream<K?, VR>?
//
//  fun <VO, VR> leftJoin(
//    otherStream: KStream<K?, VO>?,
//    joiner: ValueJoinerWithKey<in K?, in V?, in VO, out VR>?,
//    windows: JoinWindows?,
//    streamJoined: StreamJoined<K?, V?, VO>?
//  ): KStream<K?, VR>?
//
//  fun <VO, VR> outerJoin(
//    otherStream: KStream<K?, VO>?,
//    joiner: ValueJoinerWithKey<in K?, in V?, in VO, out VR>?,
//    windows: JoinWindows?,
//    streamJoined: StreamJoined<K?, V?, VO>?
//  ): KStream<K?, VR>?
//
//  fun <VR> flatTransformValues(
//    valueTransformerSupplier: ValueTransformerWithKeySupplier<in K?, in V?, Iterable<VR>?>?,
//    named: Named?,
//    vararg stateStoreNames: String?
//  ): KStream<K?, VR>?
//
//  fun <VR> transformValues(
//    valueTransformerSupplier: ValueTransformerWithKeySupplier<in K?, in V?, out VR>?,
//    named: Named?,
//    vararg stateStoreNames: String?
//  ): KStream<K?, VR>?
//
//  fun merge(stream: KStream<K?, V?>?, named: Named?): KStream<K?, V?>?
//  fun repartition(repartitioned: Repartitioned<K?, V?>?): KStream<K?, V?>?
//  fun process(
//    processorSupplier: ProcessorSupplier<in K?, in V?, Void?, Void?>?,
//    named: Named?,
//    vararg stateStoreNames: String?
//  )
//
//  fun to(topic: String?, produced: Produced<K?, V?>?)
//  fun to(topicExtractor: TopicNameExtractor<K?, V?>?, produced: Produced<K?, V?>?)
//
