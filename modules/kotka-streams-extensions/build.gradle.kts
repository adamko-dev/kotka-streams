plugins {
  kotka.convention.lang.`kotlin-jvm`
  kotka.convention.release.`maven-publish`
}

dependencies {
  api("org.apache.kafka:kafka-streams:3.0.0")
}
