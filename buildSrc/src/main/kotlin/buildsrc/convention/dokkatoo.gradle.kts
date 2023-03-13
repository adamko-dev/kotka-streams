package buildsrc.convention

import buildsrc.ext.libs

plugins {
  id("dev.adamko.dokkatoo-html")
}

val kafkaJavadocUrl = libs.versions.kafka.map { v ->
  val (major, minor) = v.split(".")
  "https://kafka.apache.org/${major}${minor}/javadoc/"
}

dokkatoo {
  dokkatooSourceSets.configureEach {
    externalDocumentationLinks.create("kafka-streams") {
      enabled.set(true)
      url(kafkaJavadocUrl)
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
