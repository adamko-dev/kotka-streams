plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
  buildsrc.convention.dokkatoo
}

description = "A light framework for structuring Kafka Streams topics and records"


dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)

  implementation(libs.kafka.streams)

  testImplementation(libs.kotest.runnerJUnit5)
  testImplementation(libs.kotest.assertionsCore)
  testImplementation(libs.kotest.property)
  testImplementation(libs.kotest.assertionsJson)

  testImplementation(libs.mockk)
}


kotkaPublishing {
  mavenPomSubprojectName.set("Framework")
}

dokkatoo {
  moduleName.set("kotka-streams-framework")
  dokkatooSourceSets.configureEach {
    includes.from("module.md")
  }
}
