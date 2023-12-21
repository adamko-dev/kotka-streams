import buildsrc.ext.excludeGeneratedGradleDsl
import buildsrc.ext.initIdeProjectLogo

plugins {
  buildsrc.convention.`kotlin-jvm`
  buildsrc.convention.`maven-publish`
  `project-report`
  // `build-dashboard` // incompatible with Gradle CC
  idea
}

group = "dev.adamko.kotka"
version = object {
  private val gitVersion = project.gitVersion
  override fun toString(): String = gitVersion.get()
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

initIdeProjectLogo("docs/images/logo-icon.svg")

val projectVersion by tasks.registering {
  description = "prints the project version"
  group = "help"
  val version = providers.provider { project.version }
  inputs.property("version", version)
  outputs.cacheIf("logging task, it should always run") { false }
  doLast {
    logger.quiet("${version.orNull}")
  }
}
