plugins {
  buildsrc.convention.`kotlin-jvm`
  kotlin("plugin.serialization")
  buildsrc.convention.`maven-publish`
}

@Suppress("UnstableApiUsage") // version catalogs + platform() is incubating
dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)

  implementation(libs.kafka.streams)

  implementation(libs.kotlinxSerialization.core)

  testImplementation(libs.kotlinxSerialization.cbor)
  testImplementation(libs.kotlinxSerialization.json)
}


tasks.compileTestKotlin {
  // use experimental binary formats for testing
  kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
