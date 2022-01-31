rootProject.name = "kotka-streams"


include(
  ":modules:kotka-streams-extensions",
  ":modules:kotka-streams-framework",
  ":modules:kotka-streams-kotlinx-serialization",
)

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

apply(from = "./buildSrc/repositories.settings.gradle.kts")

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}
