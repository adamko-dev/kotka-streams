plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
}

description = "Kotlin extensions for Kafka Streams"


dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  implementation(libs.kafka.streams)

  implementation("org.jetbrains.kotlinx:kotlinx-html:0.8.1")
}


kotkaPublishing {
  mavenPomSubprojectName.set("Kafka Streams Extensions")
}
