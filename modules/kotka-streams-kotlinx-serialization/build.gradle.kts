plugins {
  buildsrc.convention.`kotlin-jvm`
  kotlin("plugin.serialization")
  buildsrc.convention.`maven-publish`
  buildsrc.convention.dokkatoo
}

description = "Use Kotlinx Serialization for topic key/value serdes"


dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)

  implementation(libs.kafka.streams)

  implementation(libs.slf4jApi)

  implementation(libs.kotlinxSerialization.core)

  testImplementation(libs.kotlinxSerialization.cbor)
  testImplementation(libs.kotlinxSerialization.json)
}


tasks.compileTestKotlin {
  // use experimental binary formats for testing
  kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
}


kotkaPublishing {
  mavenPomSubprojectName.set("Kotlinx Serialization Extensions")
}

dokkatoo {
  moduleName.set("kotka-streams-kotlinx-serialization")
  dokkatooSourceSets.configureEach {
    includes.from("module.md")
  }
}
