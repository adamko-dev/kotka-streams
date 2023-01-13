package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import dev.adamko.kotka.site.components.ContainerInSection
import dev.adamko.kotka.site.style.WtCols
import dev.adamko.kotka.site.style.WtOffsets
import dev.adamko.kotka.site.style.WtRows
import dev.adamko.kotka.site.style.WtSections
import dev.adamko.kotka.site.style.WtTexts
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun JoinUs() {
  ContainerInSection(WtSections.wtSectionBgGrayLight) {
    Div(attrs = {
      classes(WtRows.wtRow, WtRows.wtRowSizeM)
    }) {
      Div(attrs = {
        classes(WtCols.wtCol9, WtCols.wtColMd11, WtCols.wtColSm12)
      }) {

        P(attrs = {
          classes(WtTexts.wtSubtitle2)
        }) {
          Text("Interested in Compose for other platforms?")

          P {
            Text("Have a look at ")
            A(href = "https://www.jetbrains.com/lp/compose/", attrs = {
              classes(WtTexts.wtLink)
              target(ATarget.Blank)
            }) {
              Text("Compose Multiplatform")
            }
          }
        }

        P(attrs = {
          classes(WtTexts.wtSubtitle2, WtOffsets.wtTopOffset24)
        }) {
          Text("Feel free to join the ")
          LinkToSlack(
            url = "https://slack-chats.kotlinlang.org/c/compose-web",
            text = "#compose-web"
          )
          Text(" channel on Kotlin Slack to discuss Compose for Web, or ")
          LinkToSlack(
            url = "https://slack-chats.kotlinlang.org/c/compose",
            text = "#compose"
          )
          Text(" for general Compose discussions")
        }
      }
    }

    A(attrs = {
      classes(WtTexts.wtButton, WtTexts.wtButtonContrast, WtOffsets.wtTopOffset24)
      target(ATarget.Blank)
    }, href = "https://surveys.jetbrains.com/s3/kotlin-slack-sign-up") {
      Text("Join Kotlin Slack")
    }
  }
}

@Composable
private fun LinkToSlack(url: String, text: String) {
  A(
    href = url,
    attrs = {
      target(ATarget.Blank)
      classes(WtTexts.wtLink)
    },
  ) {
    Text(text)
  }
}
