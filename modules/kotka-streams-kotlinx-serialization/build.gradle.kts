plugins {
  kotka.convention.`kotlin-jvm`
  kotlin("plugin.serialization")
  kotka.convention.`maven-publish`
}

dependencies {
  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)

  api("org.apache.kafka:kafka-streams:3.0.0")

  api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
}
