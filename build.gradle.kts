plugins {
  kotka.convention.`kotlin-jvm`

  kotka.convention.`maven-publish`
  id("me.qoomon.git-versioning")

  `project-report`
  `build-dashboard`

  id("org.jetbrains.kotlinx.kover")
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
  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsFramework)
  api(projects.modules.kotkaStreamsKotlinxSerialization)
}

tasks.wrapper {
  gradleVersion = "7.4.1"
  distributionType = Wrapper.DistributionType.ALL
}
