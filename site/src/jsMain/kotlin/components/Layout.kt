package dev.adamko.kotka.site.components

import androidx.compose.runtime.Composable
import dev.adamko.kotka.site.content.classes2
import dev.adamko.kotka.site.style.WtContainer
import dev.adamko.kotka.site.style.WtOffsets
import dev.adamko.kotka.site.style.WtSections
import org.jetbrains.compose.web.dom.*

@Composable
fun layout(content: @Composable () -> Unit) {
  Div(
    attrs = {
      attr("data-bs-theme", "dark")
//      classes2("container-fluid d-flex flex-column min-vh-100")
//      style {
////      display(DisplayStyle.Flex)
////      flexDirection(FlexDirection.Column)
////      height(100.percent)
////      margin(0.px)
////      boxSizing("border-box")
//      }
    }) {
    content()
  }
}

@Composable
fun mainContent(content: @Composable () -> Unit) {
  Main({
    classes2("mx-4 my-2")
//    style {
//      flex("1 0 auto")
//      boxSizing("border-box")
//    }
  }) {
    content()
  }
}

@Composable
fun ContainerInSection(sectionThemeStyleClass: String? = null, content: @Composable () -> Unit) {
  Section({
    if (sectionThemeStyleClass != null) {
      classes(WtSections.wtSection, sectionThemeStyleClass)
    } else {
      classes(WtSections.wtSection)
    }
  }) {
    Div({
      classes(WtContainer.wtContainer, WtOffsets.wtTopOffset96)
    }) {
      content()
    }
  }
}
