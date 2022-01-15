
plugins {
  base
  `project-report`
  `build-dashboard`
  kotka.convention.`kotlin-jvm`
  kotka.convention.`maven-publish`
  id("me.qoomon.git-versioning")
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
  api(projects.modules.kotkaStreamsTopicData)
  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsKotlinxSerialization)
}

tasks.wrapper {
  gradleVersion = "7.3.3"
  distributionType = Wrapper.DistributionType.ALL
}
