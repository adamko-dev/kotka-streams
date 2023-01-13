package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*


@Composable
fun footer() {
  Footer2("mt-2 mx-2 bg-dark") {
    BFlexRow(classes = "align-items-end bg-body-tertiary p-4") {
      copyright()
    }
  }
}

@Composable
private fun copyright() {
  Div {
    Span { Text("Copyright © 2023") }
  }
}
