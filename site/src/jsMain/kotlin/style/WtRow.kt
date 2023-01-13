package dev.adamko.kotka.site.style

import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.css.media
import org.jetbrains.compose.web.css.mediaMaxWidth
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.value

object WtRows : StyleSheet(AppStylesheet) {

  val wtRow by style {
    AppCSSVariables.wtHorizontalLayoutGutter(0.px)
    display(DisplayStyle.Flex)
    flexWrap(FlexWrap.Wrap)

    property(
      "margin-right",
      "calc(-1*${AppCSSVariables.wtHorizontalLayoutGutter.value()})"
    )
    property(
      "margin-left",
      "calc(-1*${AppCSSVariables.wtHorizontalLayoutGutter.value()})"
    )
    boxSizing("border-box")
  }

  val wtRowSizeM by style {
    AppCSSVariables.wtHorizontalLayoutGutter(16.px)

    media(mediaMaxWidth(640.px)) {
      self style {
        AppCSSVariables.wtHorizontalLayoutGutter(8.px)
      }
    }
  }

  val wtRowSizeXs by style {
    AppCSSVariables.wtHorizontalLayoutGutter(6.px)
  }

  val wtRowSmAlignItemsCenter by style {
    self style {
      alignItems(AlignItems.Center)
    }
  }
}
