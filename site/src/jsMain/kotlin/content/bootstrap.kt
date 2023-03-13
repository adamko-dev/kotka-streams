@file:Suppress("FunctionName", "EnumEntryName")

package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import dev.adamko.kotka.site.content.ContainerSize.Companion.cssClass
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.*


@Composable
fun BContainer(
  size: ContainerSize? = null,
  classes: String = "",
  attrs: AttrBuilderContext<HTMLDivElement> = {},
  content: @Composable () -> Unit,
) {
  Div(
    attrs = {
      classes(size.cssClass)
      classes2(classes)
      attrs()
    }
  ) {
    content()
  }
}

enum class ContainerSize {
  sm,
  md,
  lg,
  xl,
  xxl,
  fluid,
  ;

  companion object {
    val ContainerSize?.cssClass get() = if (this != null) "container-$name" else "container"
  }
}


@Composable
fun BRow(
  attrs: AttrBuilderContext<HTMLDivElement> = {},
  classes: String = "",
  content: @Composable () -> Unit,
) {
  Div(
    attrs = {
      classes("row")
      classes2(classes)
      attrs()
    }
  ) {
    content()
  }
}


@Composable
fun BCol(
  attrs: AttrBuilderContext<HTMLDivElement> = {},
  classes: String = "",
  content: @Composable () -> Unit,
) {
  Div(
    attrs = {
      classes("col")
      classes2(classes)
      attrs()
    }
  ) {
    content()
  }
}


@Composable
fun BFlexRow(
  attrs: AttrBuilderContext<HTMLDivElement> = {},
  classes: String = "",
  content: @Composable () -> Unit,
) {
  Div2(
    attrs = {
      classes2("d-flex")
      classes2(classes)
      attrs()
    }
  ) {
    content()
  }
}


@Composable
fun BFlexCol(
  attrs: AttrBuilderContext<HTMLDivElement> = {},
  classes: String = "",
  content: @Composable () -> Unit,
) {
  Div2(
    attrs = {
      classes2("d-flex flex-column")
      classes2(classes)
      attrs()
    }
  ) {
    content()
  }
}
