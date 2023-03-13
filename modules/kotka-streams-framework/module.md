# Module kotka-streams-framework

A light framework for structuring topics and records.

```kotlin
  implementation("dev.adamko.kotka:kotka-streams-framework:$kotkaVersion")
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
