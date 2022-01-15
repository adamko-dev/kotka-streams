plugins {
  kotka.convention.lang.`kotlin-jvm`
  kotka.convention.release.`maven-publish`
}

dependencies {
  api(projects.modules.kotkaStreamsExtensions)
  api("org.apache.kafka:kafka-streams:3.0.0")
}
