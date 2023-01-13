import buildsrc.ext.excludeGeneratedGradleDsl
import buildsrc.ext.initIdeProjectLogo

plugins {
  buildsrc.convention.`kotlin-jvm`

  buildsrc.convention.`maven-publish`
  me.qoomon.`git-versioning`

  `project-report`
  // `build-dashboard` // incompatible with Gradle CC

  idea
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
  gradleVersion = "7.6"
  distributionType = Wrapper.DistributionType.ALL
}


idea {
  module {
    isDownloadSources = true
    excludeGeneratedGradleDsl(layout)
    excludeDirs = excludeDirs + layout.files(
      ".idea",
      "gradle/wrapper",
    )
  }
}

initIdeProjectLogo("site/src/jsMain/resources/ks-logo.svg")
