package dev.adamko.kotka.extensions

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes


/**
 * Create a [ListSerde][Serdes.ListSerde].
 *
 * @see Serdes.ListSerde
 */
@Suppress("FunctionName")
fun <Inner> ListSerde(
  valueSerde: Serde<Inner>,
): Serde<MutableList<Inner>> {
  @Suppress("UNCHECKED_CAST")
  val listClass = ArrayList::class.java as Class<ArrayList<Inner>>

  return Serdes.ListSerde<ArrayList<Inner>, Inner>(
    listClass,
    valueSerde
  )
}
