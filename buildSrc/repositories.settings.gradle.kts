pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}


fun RepositoryHandler.jitpack() {
  maven("https://jitpack.io")
}
