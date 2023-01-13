package dev.adamko.kotka.site.style

import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.value

object WtSections : StyleSheet(AppStylesheet) {

  val wtSection by style {
    boxSizing("border-box")
    paddingBottom(96.px)
    paddingTop(1.px)
    property(
      propertyName = "padding-bottom",
      value = "calc(4*${AppCSSVariables.wtOffsetTopUnit.value(24.px)})"
    )
    backgroundColor(Color("#fff"))
  }

  val wtSectionBgGrayLight by style {
    backgroundColor(Color("#f4f4f4"))
    backgroundColor(AppCSSVariables.wtColorGreyLight.value())
  }

  val wtSectionBgGrayDark by style {
    backgroundColor(Color("#323236"))
    backgroundColor(AppCSSVariables.wtColorGreyDark.value())
    height(5.em)
  }
}
