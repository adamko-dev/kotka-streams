@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

  repositories {
    mavenCentral()
    gradlePluginPortal()
    jitpack()
    sonatypeSnapshots()
  }

  pluginManagement {
    repositories {
      gradlePluginPortal()
      mavenCentral()
      jitpack()
      sonatypeSnapshots()
    }
  }

}


fun RepositoryHandler.jitpack() {
  maven("https://jitpack.io")
}


fun RepositoryHandler.sonatypeSnapshots() {
//  maven("https://oss.sonatype.org/content/repositories/snapshots") {
//    mavenContent { snapshotsOnly() }
//  }
}
