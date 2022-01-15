plugins {
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
}

dependencies {
  api(projects.modules.kotkaStreamsExtensions)
  api("org.apache.kafka:kafka-streams:3.0.0")
}
