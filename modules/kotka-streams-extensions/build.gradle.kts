plugins {
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
}

dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  implementation(libs.kafka.streams)
}
