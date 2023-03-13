rootProject.name = "kotka-streams"


include(
  ":docs",

  ":modules:kotka-streams-extensions",
  ":modules:kotka-streams-framework",
  ":modules:kotka-streams-kotlinx-serialization",
  ":modules:versions-platform",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

apply(from = "./buildSrc/repositories.settings.gradle.kts")

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}
