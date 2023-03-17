rootProject.name = "kotka-streams"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  repositories {
    mavenCentral()
  }
}

include(
  ":docs",

  ":modules:kotka-streams-extensions",
  ":modules:kotka-streams-framework",
  ":modules:kotka-streams-kotlinx-serialization",
  ":modules:versions-platform",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
