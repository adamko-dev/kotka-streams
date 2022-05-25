plugins {
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
}

dependencies {
  implementation(libs.kafka.streams)
}
