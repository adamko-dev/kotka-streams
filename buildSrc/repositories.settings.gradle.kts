@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

  repositories {
    mavenCentral()
    gradlePluginPortal()
    jitpack()
  }

  pluginManagement {

    repositories {
      gradlePluginPortal()
      mavenCentral()
      jitpack()
    }

  }

}

fun RepositoryHandler.jitpack() {
  maven("https://jitpack.io")
}
