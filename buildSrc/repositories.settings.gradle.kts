@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {

  repositories {
    mavenCentral()
    gradlePluginPortal()
    jitpack()
    jetBrainsCompose()
    jetBrainsKotlinxHtml()

    // Declare the Node.js & Yarn download repositories
    exclusiveContent {
      forRepository {
        ivy("https://nodejs.org/dist/") {
          name = "Node Distributions at $url"
          patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
          metadataSources { artifact() }
          content { includeModule("org.nodejs", "node") }
        }
      }
      filter { includeGroup("org.nodejs") }
    }

    exclusiveContent {
      forRepository {
        ivy("https://github.com/yarnpkg/yarn/releases/download") {
          name = "Yarn Distributions at $url"
          patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
          metadataSources { artifact() }
          content { includeModule("com.yarnpkg", "yarn") }
        }
      }
      filter { includeGroup("com.yarnpkg") }
    }
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

fun RepositoryHandler.jetBrainsCompose() {
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
    mavenContent {
      includeGroupByRegex("""androidx\..+""")
      includeGroupByRegex("""org\.jetbrains\..+""")
      includeGroup("web")
      includeGroup("com.theapache64.composebird")
    }
  }
}

fun RepositoryHandler.jetBrainsKotlinxHtml() {
  maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") {
    mavenContent {
      includeGroup("org.jetbrains.kotlinx")
    }
  }
}
