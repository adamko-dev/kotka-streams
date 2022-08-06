plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
}

dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)

  implementation(libs.kafka.streams)
}
