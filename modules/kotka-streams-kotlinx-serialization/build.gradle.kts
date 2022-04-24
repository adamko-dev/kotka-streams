plugins {
  kotka.convention.`kotlin-jvm`
  kotlin("plugin.serialization")
  kotka.convention.`maven-publish`
}

@Suppress("UnstableApiUsage") // version catalogs + platform() is incubating
dependencies {
  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)

  api(libs.kafka.streams)

  api(platform(libs.kotlinx.serialization.bom))
  api(libs.kotlinx.serialization.core)
}
