package dev.adamko.kotka.site.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.adamko.kotka.site.externals.HighlightJs
import dev.adamko.kotka.site.namingOperators2
import kotlinx.html.dom.append
import kotlinx.html.head
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.asList


import kotlinx.html.html
import kotlinx.html.stream.appendHTML
import kotlinx.html.title

@Composable
fun examplesSection() {
  Section {
    DisposableEffect("namingOperators") {
      scopeElement.append
        .namingOperators2()
        .querySelectorAll("code")
        .asList()
        .filterIsInstance<HTMLElement>()
        .forEach(HighlightJs.Companion::highlightElement)
      onDispose { }
    }
  }
}

//@Composable
//fun namingOperators() {
//  Div2 {
//
//    H5 { Text("Naming Operators") }
//
//    Div {
//      P {
//        Text("Kafka Stream operations can be named using the ")
//        externalLink(
//          text = "naming operators",
//          url = "https://kafka.apache.org/documentation/streams/developer-guide/dsl-topology-naming.html",
//        )
//      }
//
//      Ul {
//        Li { codeSnippetInline("Grouped") }
//        Li { codeSnippetInline("StreamJoined") }
//        Li { codeSnippetInline("Joined") }
//        Li { codeSnippetInline("StreamJoined") }
//        Li { codeSnippetInline("Materialized") }
//        Li { codeSnippetInline("Named") }
//      }
//
//      P {
//        Text("However, the names are set using the ")
//        codeSnippetInline("as()")
//        Text(" function, which is a keyword in Kotlin, and so it must be escaped using backticks:")
//      }
//
//      formattedCodeSnippetKt(
//        """
//          val stream: KStream<String, String> = builder.stream(
//              "input",
//              // 'as' is a keyword - must use backticks
//              Consumed.`as`("Customer_transactions_input_topic")
//          )
//        """.trimIndent()
//      ) { testCode("example-naming-operators-01.kt") }
//
//      P {
//        Text("Kotka streams provides an extension function, ")
//        codeSnippetInline("consumedAs()")
//        Text(" that performs the same functionality.")
//      }
//
//      formattedCodeSnippetKt(
//        """
//          import dev.adamko.kotka.extensions.*
//
//          val stream: KStream<String, String> = builder.stream(
//              "input",
//              consumedAs("Customer_transactions_input_topic")
//          )
//        """.trimIndent()
//      ) { testCode("example-naming-operators-02.kt") }
//
//      formattedCodeSnippetPlaintext(
//        """
//          Topologies:
//             Sub-topology: 0
//              Source: Customer_transactions_input_topic (topics: [input])
//        """.trimIndent()
//      ) {
//        testExpected("example-naming-operators-01.kt", "example-naming-operators-02.kt")
//      }
//    }
//  }
//}

private fun AttrsScope<HTMLElement>.testCode(filename: String, vararg filenames: String) {
  attr("data-test-code", (listOf(filename) + filenames).joinToString(","))
}


private fun AttrsScope<HTMLElement>.testExpected(filename: String, vararg filenames: String) {
  attr("data-test-expected", (listOf(filename) + filenames).joinToString(","))
}
