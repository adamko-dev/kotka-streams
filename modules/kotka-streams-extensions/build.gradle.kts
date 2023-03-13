plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
  buildsrc.convention.dokkatoo
}

description = "Kotlin extensions for Kafka Streams"


dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  implementation(libs.kafka.streams)
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
