package dev.adamko.kotka.site.style

import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.boxSizing
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.media
import org.jetbrains.compose.web.css.mediaMaxWidth
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.overflow
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba

object WtCards : StyleSheet(AppStylesheet) {
  val wtCard by style {
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    border(1.px, LineStyle.Solid)
    minHeight(0.px)
    boxSizing("border-box")
  }

  val wtCardThemeLight by style {
    border(color = rgba(39, 40, 44, .2))
    color(Color("#27282c"))
    backgroundColor(Color("white"))
  }

  val wtCardThemeDark by style {
    backgroundColor(rgba(255, 255, 255, 0.05))
    color(rgba(255, 255, 255, 0.6))
    border(0.px)
  }

  val wtCardSection by style {
    position(Position.Relative)
    overflow("auto")
    flex("1 1 auto")
    minHeight(0.px)
    boxSizing("border-box")
    padding(24.px, 32.px)

    media(mediaMaxWidth(640.px)) {
      self style { padding(16.px) }
    }
  }

  val wtVerticalFlex by style {
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.FlexStart)
  }

  val wtVerticalFlexGrow by style {
    flexGrow(1)
    maxWidth(100.percent)
  }
}
