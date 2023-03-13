package dev.adamko.kotka.site

//import dev.adamko.kxstsgen.App

import androidx.compose.runtime.Composable
import dev.adamko.kotka.site.components.layout
import dev.adamko.kotka.site.components.mainContent
import dev.adamko.kotka.site.content.examplesSection
import dev.adamko.kotka.site.content.footer
import dev.adamko.kotka.site.content.header
import dev.adamko.kotka.site.content.introSection
import dev.adamko.kotka.site.style.AppStylesheet2
import org.jetbrains.compose.web.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*


//fun main() {
//  onWasmReady {
//    Window("Falling Balls") {
//      App()
//    }
//  }
//}

fun main() {
  renderComposable(rootElementId = "root") {
    Style(AppStylesheet2)

    layout {
      header()

      mainContent {
        introSection()
        examplesSection()

        //        ComposeWebLibraries()
//        GetStarted()
//        CodeSamples()
//        JoinUs()
      }
//
      footer()
    }
  }
}


@Composable
fun loremIpsum() {
  P {
    Text(
      """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. A iaculis at erat pellentesque. At erat pellentesque adipiscing commodo. Et magnis dis parturient montes nascetur. Lacinia at quis risus sed vulputate odio ut. Cras sed felis eget velit aliquet sagittis id consectetur. Vestibulum rhoncus est pellentesque elit. Nisl purus in mollis nunc sed id. Dignissim sodales ut eu sem integer vitae justo eget magna. Eu augue ut lectus arcu bibendum at varius vel pharetra. Semper auctor neque vitae tempus. Diam in arcu cursus euismod quis viverra. Ut tellus elementum sagittis vitae et leo duis ut. Adipiscing commodo elit at imperdiet dui. Enim facilisis gravida neque convallis a cras semper auctor neque. Cursus eget nunc scelerisque viverra. Consectetur lorem donec massa sapien faucibus et molestie. Adipiscing vitae proin sagittis nisl rhoncus mattis. Eu mi bibendum neque egestas. Duis ut diam quam nulla porttitor massa id neque aliquam.
      """.trimIndent()
    )
  }
  P {
    Text(
      """
        Fermentum leo vel orci porta non pulvinar neque. Sagittis eu volutpat odio facilisis mauris sit amet. Condimentum mattis pellentesque id nibh tortor id aliquet. Amet tellus cras adipiscing enim eu. A scelerisque purus semper eget duis at tellus at. A pellentesque sit amet porttitor eget dolor morbi. Viverra vitae congue eu consequat ac felis donec et odio. Egestas pretium aenean pharetra magna ac. Magna fringilla urna porttitor rhoncus dolor purus non. Senectus et netus et malesuada. Ut aliquam purus sit amet.
      """.trimIndent()
    )
  }
  P {
    Text(
      """
        Mattis ullamcorper velit sed ullamcorper morbi tincidunt. Eget gravida cum sociis natoque. Cursus eget nunc scelerisque viverra mauris in aliquam sem fringilla. Fermentum odio eu feugiat pretium. Morbi tristique senectus et netus et. Vitae auctor eu augue ut lectus arcu. Nulla facilisi morbi tempus iaculis urna. Congue nisi vitae suscipit tellus mauris. Turpis massa sed elementum tempus egestas sed sed risus. Massa sapien faucibus et molestie. Rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque.
      """.trimIndent()
    )
  }

  P {
    Text(
      """
        Amet tellus cras adipiscing enim eu turpis. Morbi tincidunt augue interdum velit euismod in. Blandit cursus risus at ultrices mi tempus imperdiet nulla malesuada. Nunc aliquet bibendum enim facilisis gravida neque convallis. Imperdiet sed euismod nisi porta lorem mollis aliquam ut porttitor. Commodo elit at imperdiet dui accumsan. Erat nam at lectus urna. Eget est lorem ipsum dolor. Lectus quam id leo in. Dolor sit amet consectetur adipiscing. Amet consectetur adipiscing elit duis tristique sollicitudin nibh sit amet.
      """.trimIndent()
    )
  }

  P {
    Text(
      """
        Donec et odio pellentesque diam volutpat commodo sed. Sed vulputate mi sit amet mauris commodo. Est lorem ipsum dolor sit amet consectetur. Porta nibh venenatis cras sed felis eget velit. Turpis egestas sed tempus urna et pharetra. Ultricies lacus sed turpis tincidunt id aliquet risus feugiat in. Orci dapibus ultrices in iaculis nunc sed augue lacus. Quis eleifend quam adipiscing vitae proin sagittis. Sit amet venenatis urna cursus eget nunc scelerisque. Semper eget duis at tellus.
      """.trimIndent()
    )
  }
}
