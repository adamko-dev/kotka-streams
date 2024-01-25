@file:Suppress("UnstableApiUsage")

rootProject.name = "kotka-streams"

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
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
  ":docs:code",

  ":modules:kotka-streams-extensions",
  ":modules:kotka-streams-framework",
  ":modules:kotka-streams-kotlinx-serialization",
  ":modules:versions-platform",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

//region git versioning
val gitDescribe: Provider<String> =
  providers
    .exec {
      workingDir(rootDir)
      commandLine(
        "git",
        "describe",
        "--always",
        "--tags",
        "--dirty=-DIRTY",
        "--broken=-BROKEN",
        "--match=v[0-9]*\\.[0-9]*\\.[0-9]*",
      )
      isIgnoreExitValue = true
    }.standardOutput.asText.map { it.trim() }

val currentBranchName: Provider<String> =
  providers
    .exec {
      workingDir(rootDir)
      commandLine(
        "git",
        "branch",
        "--show-current",
      )
      isIgnoreExitValue = true
    }.standardOutput.asText.map { it.trim() }

val currentCommitHash: Provider<String> =
  providers.exec {
    workingDir(rootDir)
    commandLine(
      "git",
      "rev-parse",
      "--short",
      "HEAD",
    )
    isIgnoreExitValue = true
  }.standardOutput.asText.map { it.trim() }

/**
 * The standard Gradle way of setting the version, which can be set on the CLI with
 *
 * ```shell
 * ./gradlew -Pversion=1.2.3
 * ```
 *
 * This can be used to override [gitVersion].
 */
val standardVersion: Provider<String> = providers.gradleProperty("version")

/** Match simple SemVer tags. The first group is the `major.minor.patch` digits. */
val semverRegex = Regex("""v((?:0|[1-9][0-9]*)\.(?:0|[1-9][0-9]*)\.(?:0|[1-9][0-9]*))""")

val gitVersion: Provider<String> =
  gitDescribe.zip(currentBranchName) { described, branch ->
    val detached = branch.isNullOrBlank()

    if (!detached) {
      "$branch-SNAPSHOT"
    } else {
      val descriptions = described.split("-")
      val head = descriptions.singleOrNull() ?: ""
      // drop the leading `v`, try to find the `major.minor.patch` digits group
      val headVersion = semverRegex.matchEntire(head)?.groupValues?.last()
      headVersion
        ?: currentCommitHash.orNull // fall back to using the git commit hash
        ?: "unknown" // just in case there's no git repo, e.g. someone downloaded a zip archive
    }
  }

gradle.allprojects {
  extensions.add<Provider<String>>("gitVersion", standardVersion.orElse(gitVersion))
}
//endregion
