# Module kotka-streams-extensions

Contains the basic extension functions to make Kafka Streams more Kotlin-esque.

```kotlin
implementation("dev.adamko.kotka:kotka-streams-extensions:$kotkaVersion")
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
