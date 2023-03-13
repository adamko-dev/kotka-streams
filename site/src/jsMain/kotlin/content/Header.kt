package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import dev.adamko.kotka.site.content.ContainerSize.fluid
import dev.adamko.kotka.site.style.AppStylesheet2
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun header() {
//  Section {
    Nav(attrs = { classes(NavStyle.nav) }) {
      BContainer(size = fluid) {
        logo()
        Div(attrs = { classes2("ms-auto") }) {
          externalLink("GitHub", "https://github.com/adamko-dev/kotka-streams")
        }
      }
    }
//  }
}

@Composable
private fun logo() =
  A(attrs = { classes("navbar-brand") }) {
    Img(src = "ks-logo.svg", alt = "Logo", attrs = { classes(NavStyle.logo) })
//    Text("Kotka Streams")
    Span(attrs = { classes(NavStyle.logoText) }) { Text("Kotka Streams") }
  }

@Composable
fun externalLink(text: String, url: String) =
  A(
    href = url,
    attrs = {
      classes("text-decoration-none") // text-reset
      target(ATarget.Blank)
    }) {
    Span { Text(text) }
    I2(
      classes = "bi bi-box-arrow-in-up-right fw-bolder align-top",
      style = { fontSize(0.8.cssRem) }
    ) { }
  }


private object NavStyle : StyleSheet(AppStylesheet2) {
  val nav = listOf(
    "navbar",
    "navbar-dark",
    "navbar-expand-lg",
    "bg-dark",
  )

  val logo: List<String> = listOf(
    "mx-2",
    "d-inline-block",
    "align-text-top",
  )

  val logoText: List<String> = listOf(
  )

}
