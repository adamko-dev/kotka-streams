package dev.adamko.kotka.site.content

import androidx.compose.runtime.*
import dev.adamko.kotka.site.components.ContainerInSection
import dev.adamko.kotka.site.externals.HighlightJs
import dev.adamko.kotka.site.style.WtCols
import dev.adamko.kotka.site.style.WtOffsets
import dev.adamko.kotka.site.style.WtRows
import dev.adamko.kotka.site.style.WtTexts
import org.intellij.lang.annotations.Language
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.attributes.AutoComplete.Companion.language
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.DisplayStyle.Companion.Inline
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement


private val SimpleCounterSnippet = CodeSnippetData(
  title = "Simple Counter using Composable DOM",
  source = """
        fun main() {
            val count = mutableStateOf(0)

            renderComposable(rootElementId = "root") {
                Button(attrs = {
                    onClick { count.value = count.value - 1 }
                }) {
                    Text("-")
                }
                Span(attrs = { style { padding(15.px) }}) { /* we use inline style here */
                    Text("${"$"}{count.value}")
                }
                Button(attrs = {
                    onClick { count.value = count.value + 1 }
                }) {
                    Text("+")
                }
            }
        }
    """.trimIndent()
)

private val DeclareAndUseStylesheet = CodeSnippetData(
  title = "Declare and use a stylesheet",
  source = """
        object MyStyleSheet : StyleSheet() {
            val container by style { /* define a class `container` */
                border(1.px, LineStyle.Solid, Color.RGB(255, 0, 0))
            }
        }

        @Composable
        fun MyComponent() {
            Div(attrs = {
                classes(MyStyleSheet.container) /* use `container` class */
            }) {
                Text("Hello world!")
            }
        }

        fun main() {
            renderComposable(rootElementId = "root") {
                Style(MyStyleSheet) /* mount the stylesheet */
                MyComponent()
            }
        }
    """.trimIndent()
)

private val DeclareAndUseCssVariable = CodeSnippetData(
  title = "Declare and use CSS variables",
  source = """
        object MyVariables {
            val contentBackgroundColor by variable<Color>() /* declare a variable */
        }

        object MyStyleSheet: StyleSheet() {
            val container by style {
                MyVariables.contentBackgroundColor(Color("blue")) /* set its value */
            }
            val content by style {
                backgroundColor(MyVariables.contentBackgroundColor.value()) /* use it */
            }
        }

        @Composable
        fun MyComponent() {
            Div(attrs = {
                classes(MyStyleSheet.container)
            }) {
                Span(attrs = {
                    classes(MyStyleSheet.content)
                }) {
                    Text("Hello world!")
                }
            }
        }
    """.trimIndent()
)

private val HoverSelectorAndMedia = CodeSnippetData(
  title = "Hover selector and media query examples",
  source = """
        object MyStyleSheet: StyleSheet() {
            val container by style {

                backgroundColor(Color("blue"))

                padding(20.px)

                hover(self) style { /* `self` is a reference to the class */
                    backgroundColor(Color("red"))
                }

                media(maxWidth(500.px)) {
                    self style {
                        padding(10.px)
                    }
                }
            }
        }
    """.trimIndent()
)

private val DefineCssClassInComponent = CodeSnippetData(
  title = "Define a CSS class in a component",
  source = """
        object MyStyleSheet: StyleSheet() {}

        @Composable
        fun MyComponent() {
            Div(attrs = {
                /* the class name will be generated at runtime */
                classes(MyStyleSheet.css {

                    backgroundColor(Color("blue"))

                    self + ":hover" style { /* this is an example of a raw selector */
                        backgroundColor(Color("red"))
                    }
                })
            }) {
                Text("Hello world!")
            }
        }
    """.trimIndent()
)

private val LayoutsSample = CodeSnippetData(
  title = "Counter for Web and Desktop",
  source = """
        /* Shared code in commonMain - App.kt (No direct control over DOM or CSS here) */

        private val counter = mutableStateOf(0)

        @Composable
        fun App() {
            Row {
                Button(onClick = { counter.value = counter.value - 1 }) {
                    Text("-")
                }

                Text("${"$"}{counter.value}", modifier = Modifier.padding(16.dp))

                Button(onClick = { counter.value = counter.value + 1 }) {
                    Text("+")
                }
            }
        }

        /* Desktop specific code in desktopMain: */

        fun main() = Window(title = "Demo", size = IntSize(800, 800)) {
            App()
        }

        /* Web specific code in jsMain: */

        fun main() = renderComposable(rootElementId = "root") {
            App()
        }
    """.trimIndent()
)

private val allSnippets = arrayOf(
  SimpleCounterSnippet,
  DeclareAndUseStylesheet,
  DeclareAndUseCssVariable,
  HoverSelectorAndMedia,
  DefineCssClassInComponent,
  //LayoutsSample
)

private var currentCodeSnippet: CodeSnippetData by mutableStateOf(allSnippets[0])
private var selectedSnippetIx: Int by mutableStateOf(0)

@Composable
fun CodeSamples() {
  ContainerInSection {
    Div({
      classes(WtRows.wtRow)
      style {
        justifyContent(JustifyContent.SpaceBetween)
      }
    }) {
      Div({ classes(WtCols.wtCol6, WtCols.wtColMd4, WtCols.wtColSm12) }) {
        H1({
          classes(WtTexts.wtH2)
        }) {
          Text("Code samples")
        }
      }

      Div({ classes(WtOffsets.wtTopOffsetSm24) }) {
        CodeSampleSwitcher(count = allSnippets.size, current = selectedSnippetIx) {
          selectedSnippetIx = it
          currentCodeSnippet = allSnippets[it]
        }
      }
    }

    TitledCodeSample(title = currentCodeSnippet.title, code = currentCodeSnippet.source)
  }
}

@Composable
fun TitledCodeSample(title: String, code: String) {
  H3({
    classes(WtTexts.wtH3, WtOffsets.wtTopOffset48)
  }) {
    Text(title)
  }

  Div({
    classes(WtOffsets.wtTopOffset24)
    style {
      backgroundColor(rgba(39, 40, 44, 0.05))
      borderRadius(8.px, 8.px, 8.px)
      padding(12.px, 16.px)
    }
  }) {
    formattedCodeSnippetKt(code)
  }
}


@Composable
fun codeSnippetBlock(
  code: String,
  language: String = "plaintext",
  attrs: AttrsScope<HTMLElement>.() -> Unit = {},
) {
  Pre(attrs = {
    classes2("bg-black")
    attrs()
  }) {
    Code2(language) {
      DisposableEffect(code) {
        scopeElement.codeContent = code
        onDispose { }
      }
    }
  }
}

@Composable
fun codeSnippetInline(code: String, language: String = "plaintext") {
//  P {
  Code2(language, style = { display(Inline) }) {
    DisposableEffect(code) {
      scopeElement.codeContent = code
      onDispose { }
    }
  }
//  }
}

/** [Text content][org.w3c.dom.Node.textContent], formatted by [HighlightJs]. */
private var HTMLElement.codeContent
  get() = textContent
  set(value) {
    textContent = value
    HighlightJs.highlightElement(this)
  }

//private fun HTMLElement.setHighlightedCode(code: String) {
//  textContent = code
//}

@Composable
fun formattedCodeSnippetKt(
  @Language("kotlin")
  code: String,
  attrs: AttrsScope<HTMLElement>.() -> Unit = {},
) {
  codeSnippetBlock(code, "kotlin", attrs)
}

@Composable
fun formattedCodeSnippetKts(
  @Language("kts")
  code: String,
  attrs: AttrsScope<HTMLElement>.() -> Unit = {},
) {
  codeSnippetBlock(code, "kotlin", attrs)
}

@Composable
fun formattedCodeSnippetPlaintext(
  @Language("none")
  code: String,
  attrs: AttrsScope<HTMLElement>.() -> Unit = {},
) {
  codeSnippetBlock(code, "plaintext", attrs)
}

private data class CodeSnippetData(
  val title: String,
  val source: String,
)
