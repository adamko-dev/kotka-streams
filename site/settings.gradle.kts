rootProject.name = "kotka-streams-site"

apply(from = "../buildSrc/repositories.settings.gradle.kts")

//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }

  repositories {
    gitHubReleases()
    jsDelivr()
  }
}

fun RepositoryHandler.gitHubReleases() {
  ivy("https://github.com/") {
    patternLayout {
      artifact("[organisation]/releases/download/v[revision]/[module]-[revision].[ext]")
      artifact("[organisation]/[module]/archive/refs/tags/[revision].[ext]")
    }
    metadataSources { artifact() }
  }
}

fun RepositoryHandler.jsDelivr() {
  // https://cdn.jsdelivr.net/gh/highlightjs/cdn-release@$highlightJsVersion/build/$file
  ivy("https://cdn.jsdelivr.net/") {
    patternLayout {
//      artifact("gh/[organisation]/cdn-release@[revision]/build/[module]")
      artifact("gh/[organisation]/cdn-release@[revision]/build/[module].[ext]")
    }
    metadataSources { artifact() }
  }
}
