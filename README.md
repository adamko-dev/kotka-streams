[![](https://jitpack.io/v/adamko-dev/kotka-streams.svg)](https://jitpack.io/#adamko-dev/kotka-streams)

# Kotka Streams - Kotlin for Kafka Streams

Using [Kotka](https://github.com/adamko-dev/kotka-streams) means a more pleasant experience while
using [Kafka Streams](https://kafka.apache.org/documentation/streams/).


## Quickstart

Add [JitPack](https://jitpack.io/) as a repository, and add a dependency
on `kotka-streams-extensions` for the basics.

```kotlin
// build.gradle.kts
repositories {
  maven("https://jitpack.io/")
}

dependencies {
  implementation("com.github.adamko-dev.kotka-streams:kotka-streams-extensions:$kotkaVersion")
}
```

## Modules

There are three modules. Add a dependency on `com.github.adamko-dev:kotka-streams` to get them all
at once

```kotlin
dependencies {
  implementation("com.github.adamko-dev.kotka-streams:kotka-streams:$kotkaVersion")
}
```

### `kotka-streams-extensions`

Contains the basic extension functions to make Kafka Streams more Kotlin-esque.

```kotlin
  implementation("com.github.adamko-dev.kotka-streams:kotka-streams-extensions:$kotkaVersion")
```

```kotlin
import dev.adamko.kotka.extensions.tables.*
import dev.adamko.kotka.extensions.streams.*
import dev.adamko.kotka.extensions.*

data class MusicalBand(
  val name: String,
  val memberNames: List<String>,
)

builder.stream<String, MusicalBand>("musical-bands")
  .flatMap("band-member-names-to-band-name") { _: String, band: MusicalBand ->
    band.memberNames.map { memberName -> memberName to band.name }
  }
  .groupByKey(groupedAs("map-of-band-member-to-band-names"))
```

### `kotka-streams-framework`

A light framework for structuring topics and records.

```kotlin
  implementation("com.github.adamko-dev.kotka-streams:kotka-streams-framework:$kotkaVersion")
```

Use `TopicRecord` to standardise the data on each topic. Records can now easily be converted from
one type, to another.

```kotlin
import dev.adamko.kotka.extensions.tables.*
import dev.adamko.kotka.extensions.streams.*
import dev.adamko.kotka.extensions.*
import dev.adamko.kotka.topicdata.*

data class Animal(
  val id: Long,
  val name: String,
) : TopicRecord<Long> {
  override val topicKey: Long by ::id
}

data class Pet(
  val id: Long,
  val name: String,
) : TopicRecord<Long> {
  override val topicKey: Long by ::id
}

val petUpdates = builder.stream<Long, Animal>("animals")
  .mapTopicRecords("convert-animals-to-pets") { _, animal ->
    Pet(animal.id, animal.name)
  }
```

Use `KeyValueSerdes<K, V>` to define both the key and value serdes for a topic.
A `TopicDefinition<K, V>` ties both of these together.

```kotlin
/** All [Pet] updates */
object PetUpdatesTopic : TopicDefinition<Long, Animal> {
  override val topicName = "pet-updates"
  override val serdes = KeyValueSerdes(Serdes.Long(), PetSerde())
}

petUpdates
  .to(
    PetUpdatesTopic.topicName,
    PetUpdatesTopic.serdes.producer("send-pet-updates-to-pet-update-topic")
  )
```

### `kotka-streams-kotlinx-serialization`

Use [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization/) for topic key/value
serdes.

```kotlin
implementation("com.github.adamko-dev.kotka-streams:kotka-streams-kotlinx-serialization:$kotkaVersion")
```

```kotlin
import dev.adamko.kotka.extensions.tables.*
import dev.adamko.kotka.extensions.streams.*
import dev.adamko.kotka.extensions.*
import dev.adamko.kotka.topicdata.*
import dev.adamko.kotka.kxs.*

val jsonMapper = Json {}

@Serializable
data class Sku(
  val sku: String
)

@Serializable
data class ShopItem(
  val id: Sku,
  val name: String,
) : TopicRecord<Sku> {
  override val topicKey: Sku by ::id
}

object ShopItemTopic : TopicDefinition<Long, ShopItem> {
  override val topicName = "shop-item-updates"
  override val serdes = KeyValueSerdes.kxsJson(jsonMapper)
}
```
