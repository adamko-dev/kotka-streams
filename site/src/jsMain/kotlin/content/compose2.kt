@file:Suppress("FunctionName")

package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.internal.runtime.*
import org.w3c.dom.*


fun <T : Element> AttrsScope<T>.classes2(classes2: String) {
  if (classes2.isBlank()) return

  classes2
    .split(" ")
    .distinct()
    .filter { it.isNotBlank() }
    .takeIf { it.isNotEmpty() }
    ?.let(::classes)
}


@Composable
fun Div2(
  classes: String = "",
  attrs: AttrBuilderContext<HTMLDivElement> = {},
  content: ContentBuilder<HTMLDivElement> = {},
) {
  Div(
    attrs = {
      classes2(classes)
      attrs()
    },
    content = content
  )
}

@Composable
fun P2(
  classes: String = "",
  style: StyleScope.() -> Unit = {},
  attrs: AttrBuilderContext<HTMLParagraphElement> = {},
  content: ContentBuilder<HTMLParagraphElement> = {},
) {
  P(
    attrs = {
      classes2(classes)
      attrs()
      style(style)
    },
    content = content
  )
}


@Composable
fun I2(
  classes: String = "",
  style: StyleScope.() -> Unit = {},
  attrs: AttrBuilderContext<HTMLElement> = {},
  content: ContentBuilder<HTMLElement> = {},
) {
  I(
    attrs = {
      classes2(classes)
      attrs()
      style(style)
    },
    content = content
  )
}


@Composable
fun Footer2(
  classes: String = "",
  style: StyleScope.() -> Unit = {},
  attrs: AttrBuilderContext<HTMLElement> = {},
  content: ContentBuilder<HTMLElement> = {},
) {
  Footer(
    attrs = {
      classes2(classes)
      attrs()
      style(style)
    },
    content = content
  )
}


@Composable
fun Span2(
  classes: String = "",
  style: StyleScope.() -> Unit = {},
  attrs: AttrBuilderContext<HTMLSpanElement> = {},
  content: ContentBuilder<HTMLSpanElement> = {},
) {
  Span(
    attrs = {
      classes2(classes)
      attrs()
      style(style)
    },
    content = content
  )
}


@Composable
fun Section2(
  classes: String = "",
  style: StyleScope.() -> Unit = {},
  attrs: AttrBuilderContext<HTMLElement> = {},
  content: ContentBuilder<HTMLElement> = {},
) {
  Section(
    attrs = {
      classes2(classes)
      attrs()
      style(style)
    },
    content = content
  )
}

@Composable
fun Code2(
  language: String?,
  classes: String = "",
  style: StyleScope.() -> Unit = {},
  attrs: AttrBuilderContext<HTMLElement> = {},
  content: ContentBuilder<HTMLElement> = {},
) {
  Code(
    attrs = {
      if (!language.isNullOrBlank()) {
        classes("language-$language")
      }
      classes2(classes)
      attrs()
      style(style)
    },
    content = content
  )
}
