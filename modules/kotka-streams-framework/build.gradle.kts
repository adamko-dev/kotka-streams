plugins {
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
}

dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)

  implementation(libs.kafka.streams)
}
