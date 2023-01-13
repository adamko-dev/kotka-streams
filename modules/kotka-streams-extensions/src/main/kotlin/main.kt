import kotlinx.html.code
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.pre
import kotlinx.html.stream.createHTML
import kotlinx.html.unsafe

fun main() {
  println(createHTML(prettyPrint = true).div {
    pre {
      code {
        unsafe {
          raw("""
            val map: Map<String, String> = mapOf(
              "key1" to "value1",
              "key2" to "value2",
            )
          """.trimIndent())
        }
      }
    }
  })
}
