package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun introSection() {
  Section2 {
    Div {
      mainBanner()

      introText()

      // create a slideshow
      // - naming functions
      // - lambda outside of brackets
      // - integration with KxS
      // - destructured assignments
      // - auto-close KeyValueStores
    }
  }
}


@Composable
private fun mainBanner() {
  BFlexCol(
    classes = "main-banner text-center align-items-center justify-content-center",
    attrs = { style { height(25.cssRem) } },
  ) {
    Div2(classes = "h2") {
      Text("Kotka Streams")
    }
    Div2(classes = "h4") {
      Text("The Kotlin DSL for Kafka Streams")
    }
  }
}


@Composable
private fun introText() {
  BFlexCol(
    classes = "intro-text text-center align-items-center justify-content-center",
//    classes = "intro-text",
//    attrs = { style { height(25.cssRem) } },
  ) {
    P2("lead") {
      Text("Using ")
      Text("Kotka")
//      externalLink("Kotka", "https://github.com/adamko-dev/kotka-streams")
      Text(" means a more pleasant experience while using ")
      externalLink("Kafka Streams", "https://kafka.apache.org/documentation/streams/")
      Text(".")
    }
  }
}
