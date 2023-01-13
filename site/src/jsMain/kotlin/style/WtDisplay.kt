package dev.adamko.kotka.site.style

import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.media
import org.jetbrains.compose.web.css.mediaMaxWidth
import org.jetbrains.compose.web.css.px

object WtDisplay : StyleSheet(AppStylesheet) {
  val wtDisplayNone by style {
    display(DisplayStyle.None)
  }

  val wtDisplayMdBlock by style {
    media(mediaMaxWidth(1000.px)) {
      self style {
        display(DisplayStyle.Block)
      }
    }
  }

  val wtDisplayMdNone by style {
    media(mediaMaxWidth(1000.px)) {
      self style {
        display(DisplayStyle.None)
      }
    }
  }
}
