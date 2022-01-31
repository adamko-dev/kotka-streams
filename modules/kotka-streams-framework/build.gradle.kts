plugins {
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
}

dependencies {
  api(projects.modules.kotkaStreamsExtensions)

  api(libs.kafka.streams)
}
