package dev.adamko.kotka.site

import dev.adamko.kotka.site.snippets.codeSnippet
import dev.adamko.kotka.site.snippets.registeringExample
import kotlinx.html.*
import org.intellij.lang.annotations.Language


fun <T> TagConsumer<T>.namingOperators2(): T {

  return codeSnippet("Naming Operators") {
    p {
      text("Kafka Stream operations can be named using the ")
      a(href = "https://kafka.apache.org/documentation/streams/developer-guide/dsl-topology-naming.html") {
        text("naming operators")
      }
    }

    ul {
      li { inlineCode("Grouped") }
      li { inlineCode("StreamJoined") }
      li { inlineCode("Joined") }
      li { inlineCode("StreamJoined") }
      li { inlineCode("Materialized") }
      li { inlineCode("Named") }
    }

    p {
      text("However, the names are set using the ")
      inlineCode("as()")
      text(" function, and because ")
      inlineCode("as")
      text(" is a keyword in Kotlin it must be escaped using backticks:")
    }


    val consumedAsWithBackticks by registeringExample {
      codeBlockKotlin(
        """
          val stream: KStream<String, String> = builder.stream(
              "input",
              // 'as' is a keyword - must escape it with backticks
              Consumed.`as`("Customer_transactions_input_topic")
          )
        """.trimIndent()
      )
    }

    p {
      text("Kotka streams provides an extension function, ")
      inlineCodeKts("consumedAs()")
      text(" that performs the same functionality - no backticks required.")
    }

    val consumedAsWithoutBackticks by registeringExample {
      codeBlockKotlin(
        """
          import dev.adamko.kotka.extensions.*          
          
          val stream: KStream<String, String> = builder.stream(
              "input",
              consumedAs("Customer_transactions_input_topic")
          )
        """.trimIndent()
      )
    }

    p {
      text("Both functions produce the same topology, with a named Source.")
    }

    expectedResult(
      consumedAsWithBackticks, consumedAsWithoutBackticks, code = """
        Topologies:
           Sub-topology: 0
             Source: Customer_transactions_input_topic (topics: [input])
    """.trimIndent()
    )
  }
}


//fun <T> TagConsumer<T>.namingOperators2(): T {
//  return div {
//    h5 { text("Naming Operators") }
//
//    div {
//      p {
//        text("Kafka Stream operations can be named using the ")
//        a(href = "https://kafka.apache.org/documentation/streams/developer-guide/dsl-topology-naming.html") {
//          text("naming operators")
//        }
//      }
//
//      ul {
//        li { inlineCode("Grouped") }
//        li { inlineCode("StreamJoined") }
//        li { inlineCode("Joined") }
//        li { inlineCode("StreamJoined") }
//        li { inlineCode("Materialized") }
//        li { inlineCode("Named") }
//      }
//
//      p {
//        text("However, the names are set using the ")
//        inlineCode("as()")
//        text(" function, and because ")
//        inlineCode("as")
//        text(" is a keyword in Kotlin it must be escaped using backticks:")
//      }
//
//      codeBlockKotlin(
//        """
//          val stream: KStream<String, String> = builder.stream(
//              "input",
//              // 'as' is a keyword - must escape it with backticks
//              Consumed.`as`("Customer_transactions_input_topic")
//          )
//        """.trimIndent()
//      )
//
//      p {
//        text("Kotka streams provides an extension function, ")
//        inlineCodeKts("consumedAs()")
//        text(" that performs the same functionality.")
//      }
//
//      codeBlockKotlin(
//        """
//          import dev.adamko.kotka.extensions.*
//
//          val stream: KStream<String, String> = builder.stream(
//              "input",
//              consumedAs("Customer_transactions_input_topic")
//          )
//        """.trimIndent()
//      )
//
//      p {
//        text("Both functions produce the same topology, with a named Source.")
//      }
//
//      codeBlock(
//        """
//          Topologies:
//             Sub-topology: 0
//               Source: Customer_transactions_input_topic (topics: [input])
//        """.trimIndent()
//      )
//    }
//  }
//}


fun FlowContent.codeBlock(@Language("TEXT") code: String, language: String = "plaintext") {
  pre {
    code(classes = "language-$language") {
      unsafe { raw(code.replace("<", "&lt;").replace(">", "&gt;")) }
    }
  }
}

fun FlowContent.inlineCode(@Language("TEXT") code: String, language: String = "plaintext") {
  code(classes = "language-$language") {
    style = "display: inline"
    unsafe { raw(code.replace("<", "&lt;").replace(">", "&gt;")) }
  }
}


fun FlowContent.inlineCodeKotlin(@Language("kotlin") code: String) = inlineCode(code, "kotlin")
fun FlowContent.inlineCodeKts(@Language("kts") code: String) = inlineCode(code, "kotlin")
fun FlowContent.codeBlockKotlin(@Language("kotlin") code: String) = codeBlock(code, "kotlin")


//private fun AttrsScope<HTMLElement>.testCode(filename: String, vararg filenames: String) {
//  attr("data-test-code", (listOf(filename) + filenames).joinToString(","))
//}
//
//
//private fun AttrsScope<HTMLElement>.testExpected(filename: String, vararg filenames: String) {
//  attr("data-test-expected", (listOf(filename) + filenames).joinToString(","))
//}
