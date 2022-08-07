plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
}

description = "A light framework for structuring Kafka Streams topics and records"


dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)

  implementation(libs.kafka.streams)
}


kotkaPublishing {
  mavenPomSubprojectName.set("Framework")
}
