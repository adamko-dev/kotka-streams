plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
  buildsrc.convention.dokkatoo
}

description = "Kotlin extensions for Kafka Streams"


dependencies {
  api(libs.kafka.streams)

  implementation(platform(projects.modules.versionsPlatform))

  implementation(libs.kafka.streams)

  testImplementation(libs.kafka.streamsTestUtils)

  testImplementation(libs.kotest.runnerJUnit5)
  testImplementation(libs.kotest.assertionsCore)
  testImplementation(libs.kotest.property)
  testImplementation(libs.kotest.assertionsJson)

  testImplementation(libs.mockk)
}


kotkaPublishing {
  mavenPomSubprojectName.set("Kafka Streams Extensions")
}

dokkatoo {
  moduleName.set("kotka-streams-extensions")
  dokkatooSourceSets.configureEach {
    includes.from("module.md")
  }
}
