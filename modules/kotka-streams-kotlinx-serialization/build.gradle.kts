plugins {
  kotka.convention.`kotlin-jvm`
  kotlin("plugin.serialization")
  kotka.convention.`maven-publish`
}

@Suppress("UnstableApiUsage") // version catalogs + platform() is incubating
dependencies {
  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)

  implementation(libs.kafka.streams)

  implementation(platform(libs.kotlinx.serialization.bom))
  implementation(libs.kotlinx.serialization.core)

  testImplementation(libs.kotlinx.serialization.cbor)
  testImplementation(libs.kotlinx.serialization.json)
}


tasks.compileTestKotlin {
  // use experimental binary formats for testing
  kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
