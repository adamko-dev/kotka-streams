package dev.adamko.kotka.site

import dev.adamko.kotka.site.content.namingOperators
import kotlin.test.Test
import kotlinx.browser.document
import org.jetbrains.compose.web.testutils.*
import org.w3c.dom.asList

// scrape the tree, and create snippets
data class Snippet(
  val filename: String,
  val testCode: String,
  val expected: String,
)

class ExamplesTest {

  @Test
  fun testButton() = runTest {
    println("running testButton()...")
    composition {
      namingOperators()
    }

    val testCodes = document.querySelectorAll("[data-test-code]")
      .asList()
      .forEach { node ->
        println(node.textContent)
      }
    val nodes = document.querySelectorAll("[data-test-expected]")

    println(nodes.length)
    println(nodes)

    root.childNodes // ...

    val snippets: List<Snippet> = emptyList()

    // save the snippets to disk...
    snippets.forEach { snippet ->

      // testCode can be saved directly to 'filename'
      saveFile(snippet.filename, snippet.testCode)

      // also generate a test
      saveFile(
        "test-${snippet.filename}", """
        fun `test ${snippet.filename}`() {
          val output = captureOutput { main() }
          assertEquals(snippet.expected, output)
        }
      """.trimIndent()
      )
    }

    // somehow (a custom Gradle task?) run the generated tests...
  }

  fun saveFile(filename: String, content: String) {
    // save file to disk...
  }
}
