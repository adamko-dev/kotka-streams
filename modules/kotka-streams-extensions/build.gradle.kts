plugins {
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
}

dependencies {
  api(libs.kafka.streams)
  testImplementation(libs.kafka.streamsTestUtils)
}
