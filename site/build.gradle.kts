@file:Suppress("UnstableApiUsage")

import java.time.*
import java.time.format.*
import java.time.temporal.ChronoUnit
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
  buildsrc.conventions.`compose-web`
}


val highlightJsVersion = "11.7.0"

kotlin {
  js(IR) {
    browser {
      useCommonJs()
      commonWebpackConfig {
        scssSupport { enabled = true }
      }
      testTask {
        useKarma { useChromeHeadless() }
//        testLogging {
//          showCauses = true
//          showExceptions = true
//          showStackTraces = true
//          showStandardStreams = true
//          events(
//            TestLogEvent.PASSED,
//            TestLogEvent.FAILED,
//            TestLogEvent.SKIPPED,
//            TestLogEvent.STARTED,
//            TestLogEvent.STANDARD_ERROR,
//            TestLogEvent.STANDARD_OUT,
//          )
//        }
      }

      nodejs {
//        testTask {
//          testLogging {
//            showCauses = true
//            showExceptions = true
//            showStackTraces = true
//            showStandardStreams = true
//            events(
//              TestLogEvent.PASSED,
//              TestLogEvent.FAILED,
//              TestLogEvent.SKIPPED,
//              TestLogEvent.STARTED,
//              TestLogEvent.STANDARD_ERROR,
//              TestLogEvent.STANDARD_OUT,
//            )
//          }
//        }
//      }
      }
      binaries.executable()
    }
  }

  jvm()

  sourceSets {
    commonMain {
      dependencies {
        implementation(compose.ui)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.web.core)
        implementation(compose.runtime)

        implementation("org.jetbrains.kotlinx:kotlinx-html:0.8.1")
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
        implementation("com.squareup.okio:okio:3.2.0")
      }
    }

    jsMain {
      dependencies {
        implementation(compose.web.core)
        implementation(npm("highlight.js", highlightJsVersion))
      }
    }

    jsTest {
      languageSettings.optIn("org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi")

      dependencies {
        implementation(compose.web.testUtils)
      }
    }

    jvmTest {
      dependencies {
        implementation("org.junit.jupiter:junit-jupiter:5.9.1")
        implementation(project.dependencies.gradleTestKit())
      }
    }
  }
}


val bootstrapFiles by configurations.creating<Configuration> {
  isCanBeConsumed = false
  isCanBeResolved = true
  isVisible = false
}

val highlightJsFiles by configurations.creating<Configuration> {
  isCanBeConsumed = false
  isCanBeResolved = true
  isVisible = false
}


dependencies {
  bootstrapFiles("twbs/icons:bootstrap-icons:1.10.3@zip")
//  highlightJsFiles("highlightjs:highlight.js:11.7.0@zip")

  highlightJsFiles("highlightjs:styles/default:$highlightJsVersion@min.css")
  highlightJsFiles("highlightjs:styles/androidstudio:$highlightJsVersion@min.css")
//  highlightJsFiles("highlightjs:languages/kotlin:$highlightJsVersion@min.js")
//  highlightJsFiles("highlightjs:highlight.min.js:$highlightJsVersion")
//  highlightJsFiles("highlightjs:languages/typescript.min.js:$highlightJsVersion")
//  highlightJsFiles("highlightjs:styles/vs2015.min.css:$highlightJsVersion")
}

val downloadBootstrapIcons by tasks.registering(Sync::class) {
  group = "site"
  from(
    bootstrapFiles
      .incoming
      .artifactView { lenient(true) }
      .artifacts
      .resolvedArtifacts
      .map { artifacts -> artifacts.map { zipTree(it.file) } }
  ) {
    eachFile {
      // drop the first directory
      relativePath = RelativePath(true, *relativePath.segments.drop(1).toTypedArray())
    }
    includeEmptyDirs = false
    exclude("**/*.svg")
    exclude("**/*.scss")
  }
  into(temporaryDir)
}

val downloadHighlightJsFiles by tasks.registering(Sync::class) {
  group = "site"
  from(
    highlightJsFiles
      .incoming
      .artifactView { lenient(true) }
      .artifacts
      .resolvedArtifacts
      .map { artifacts -> artifacts.map { it.file } }
  ) {
    into("hljs")
  }
  into(temporaryDir)
//  outputs.upToDateWhen { false }
//  doNotTrackState("Gradle is buggy")
}

val downloadBootstrapFiles by tasks.registering(Sync::class) {
  group = "site"
  val bootstrapVersion = "5.2.3"
  val bootswatchVersion = "5.2.3"

  fun download(url: String, renamed: String) {
    val resource = resources.text.fromUri(url)
    from(resource) { rename { renamed } }
  }

  download(
    url = "https://cdn.jsdelivr.net/npm/bootstrap@$bootstrapVersion/dist/js/bootstrap.bundle.min.js",
    renamed = "bootstrap.js",
  )
  download(
    url = "https://cdn.jsdelivr.net/npm/bootswatch@$bootswatchVersion/dist/darkly/bootstrap.min.css",
    renamed = "bootstrap.css",
  )

  into(temporaryDir)
}


val installBootstrapFiles by tasks.registering(Copy::class) {
  group = "site"
  from(downloadBootstrapFiles)
  from(downloadBootstrapIcons)
  into(layout.projectDirectory.dir("src/jsMain/resources"))

  doNotTrackState("Installation directory contains unrelated files")
}


tasks.assemble {
  dependsOn(installBootstrapFiles)
  dependsOn(downloadBootstrapIcons)
  dependsOn(downloadHighlightJsFiles)
}

tasks.matching { it.name == "jsProcessResources" }.configureEach {
  dependsOn(installBootstrapFiles)
  dependsOn(downloadBootstrapIcons)
  dependsOn(downloadHighlightJsFiles)
}


// fix warning: Task ':common:jsProcessResources' uses this output of task ':common:unpackSkikoWasmRuntimeJs' ...
tasks.matching { it.name == "jsProcessResources" }.configureEach {
//  inputs.dir(
//    tasks.named<org.jetbrains.compose.experimental.web.tasks.ExperimentalUnpackSkikoWasmRuntimeTask>(
//      "unpackSkikoWasmRuntimeJs"
//    ).map { it.outputDir })
}

tasks.withType<Copy>().matching { it.name == "jsProcessResources" }.configureEach {
  from(downloadHighlightJsFiles) {
//    into(this@configureEach.destinationDir.resolve("hljs"))
  }
}

// fix warning: Task ':common:jsBrowserDevelopmentRun' uses this output of task ':common:jsDevelopmentExecutableCompileSync' ...
tasks.matching { it.name == "jsBrowserDevelopmentRun" }.configureEach {
  inputs.dir(tasks.named<Copy>("jsDevelopmentExecutableCompileSync").map { it.destinationDir })
}


tasks
  .withType<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>()
  .matching { it.name == "jsBrowserDevelopmentWebpack" }
  .configureEach {
    doLast {
      destinationDirectory
        .walk()
        .firstOrNull { it.name == "index.html" }
        ?.let { indexHtml ->
          val now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
          logger.lifecycle("[${DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now)}] file://${indexHtml.canonicalPath}")
        }
    }
  }

//tasks.matching { it.name == "jsBrowserDevelopmentRun" }.configureEach {
//  dependsOn(tasks.matching { it.name == "jsBrowserDevelopmentWebpack" })
//}


afterEvaluate {
  /**
   * `kotlin-js` adds a directory in the root-dir for the Yarn lock.
   * That's a bit annoying. It's a little neater if it's in the
   * gradle dir, next to the version-catalog.
   */
  rootProject.extensions.findByType<YarnRootExtension>()?.apply {
    lockFileDirectory = project.rootDir.resolve("gradle/kotlin-js-store")
  }
}
