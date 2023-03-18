package buildsrc.convention

import buildsrc.ext.libs
import dev.adamko.dokkatoo.DokkatooExtension

plugins {
  id("buildsrc.convention.base")
  id("dev.adamko.dokkatoo-html")
}

val kafkaBaseVersion = libs.versions.kafka.map { v ->
  val (major, minor) = v.split(".")
  "${major}${minor}"
}

val kafkaJavadocUrl = kafkaBaseVersion.map { v -> "https://kafka.apache.org/${v}/javadoc/" }
val kafkaPackageListUrl = kafkaJavadocUrl.map { "$it/element-list" }

dokkatoo {
  dokkatooSourceSets.configureEach {
    externalDocumentationLinks.create("kafka-streams") {
      enabled.set(true)
      url(kafkaJavadocUrl)
      packageListUrl(kafkaPackageListUrl)
    }
  }
}

tasks.dokkatooGeneratePublicationHtml {
  doLast {
    outputDirectory.get().asFile.walk()
      .filter { it.isFile && it.extension == "html" }
      .forEach { file ->
        file.writeText(
          file.readText()
            .replace(
              """<html>""",
              """<html lang="en">""",
            )
            .replace(
              """
                <button id="theme-toggle-button">
              """.trimIndent(),
              """
                <div id="github-link"><a href="https://github.com/adamko-dev/kotka-streams/"></a></div>
                <button id="theme-toggle-button">
              """.trimIndent(),
            ).replace(
              """
                href="https://github.com/Kotlin/dokka"><span>dokka</span>
              """.trimIndent(),
              """
                href="https://github.com/adamko-dev/dokkatoo/"><span>Dokkatoo</span>
              """.trimIndent(),
            )
        )
      }
  }
}
