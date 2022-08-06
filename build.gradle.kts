plugins {
  buildsrc.convention.`kotlin-jvm`

  buildsrc.convention.`maven-publish`
  me.qoomon.`git-versioning`

  `project-report`
  `build-dashboard`
}

group = "dev.adamko.kotka"
version = "0.0.0-SNAPSHOT"
gitVersioning.apply {
  refs {
    branch(".+") { version = "\${ref}-SNAPSHOT" }
    tag("v(?<version>.*)") { version = "\${ref.version}" }
  }

  // optional fallback configuration in case of no matching ref configuration
  rev { version = "\${commit}" }
}


dependencies {
  implementation(platform(projects.modules.versionsPlatform))

  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)
  api(projects.modules.kotkaStreamsKotlinxSerialization)
}


kotkaPublishing {
  mavenPomSubprojectName.set("Kotlin for Kafka Streams")
  mavenPomDescription.set("Using Kotka means a more pleasant experience while using Kafka Streams")
}


tasks.wrapper {
  gradleVersion = "7.5.1"
  distributionType = Wrapper.DistributionType.ALL
}
