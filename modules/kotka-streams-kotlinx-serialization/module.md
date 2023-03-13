# Module kotka-streams-kotlinx-serialization

Use [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization/) for topic key/value
serdes.

```kotlin
implementation("dev.adamko.kotka:kotka-streams-kotlinx-serialization:$kotkaVersion")
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
