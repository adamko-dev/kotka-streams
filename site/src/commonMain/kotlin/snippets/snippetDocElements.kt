package dev.adamko.kotka.site.snippets

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlinx.html.*
import org.intellij.lang.annotations.Language


fun <T> TagConsumer<T>.codeSnippet(
  title: String,
  builder: CodeSnippetContext<T>.() -> Unit,
): T {
  val tagConsumer: TagConsumer<T> = this

  return div flowContent@{
    h5 { text(title) }

    val context = CodeSnippetContext(title, tagConsumer, this@flowContent)

    context.builder()
  }
}

@DslMarker
annotation class CodeSnippetContextDslMarker


@CodeSnippetContextDslMarker
class CodeSnippetContext<T>(
  private val title: String,
  private val tagConsumer: TagConsumer<T>,
  private val flowContent: FlowContent,
) : TagConsumer<T> by tagConsumer, FlowContent by flowContent {

  private val _registeredExamples: MutableList<CodeSnippetExample> = mutableListOf()
  val registeredExamples: List<CodeSnippetExample> get() = _registeredExamples.toList()

  fun registerExample(
    name: String? = null
  ): CodeSnippetExample {

    val exampleName = "example-" + if (name.isNullOrBlank()) {
      title.lowercase().replace(Regex("[^a-zA-Z0-9]+"), "-")
      +"-${_registeredExamples.size}"
    } else {
      name.replace(Regex("[^a-zA-Z0-9]+"), "-")
    }

    val example = CodeSnippetExample(exampleName)
    _registeredExamples.add(example)
    return example
  }

  fun codeBlock(
    @Language("TEXT")
    code: String,
    language: String = "plaintext",
    configure: CODE.() -> Unit = {},
  ) {
    pre {
      code(classes = "language-$language") {
        unsafe { raw(code.replace("<", "&lt;").replace(">", "&gt;")) }

        configure()
      }
    }
  }

  fun CodeSnippetExample.inlineCode(
    @Language("TEXT") code: String,
    language: String = "plaintext"
  ) {
    code(classes = "language-$language") {
      style = "display: inline"
      unsafe { raw(code.replace("<", "&lt;").replace(">", "&gt;")) }
    }
  }

  fun CodeSnippetExample.inlineCodeKotlin(@Language("kotlin") code: String) =
    inlineCode(code, "kotlin")

  fun CodeSnippetExample.inlineCodeKts(@Language("kts") code: String) = inlineCode(code, "kotlin")

  fun CodeSnippetExample.codeBlockKotlin(@Language("kotlin") code: String) =
    codeBlock(code, "kotlin") {
      attributes["data-test-code"] = name
    }

  fun expectedResult(
    example: CodeSnippetExample,
    vararg examples: CodeSnippetExample,
    @Language("TEXT") code: String,
    language: String = "plaintext",
  ) {
    val allExamples = (listOf(example) + examples).joinToString(",") { it.name }

    codeBlock(code, language) {
      attributes["data-test-expected"] = allExamples
    }
  }
}


class CodeSnippetExample(
  val name: String,
  val imports: MutableList<String> = mutableListOf(),
  val content: MutableList<String> = mutableListOf(),
)


fun CodeSnippetContext<*>.registeringExample() = CodeSnippetExampleDelegateProvider(this)


class CodeSnippetExampleDelegate(
  private val delegated: CodeSnippetExample
) : ReadOnlyProperty<Any?, CodeSnippetExample> {
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): CodeSnippetExample =
    delegated
}

class CodeSnippetExampleDelegateProvider(
  private val context: CodeSnippetContext<*>
) : PropertyDelegateProvider<Any?, CodeSnippetExampleDelegate> {

  override operator fun provideDelegate(
    thisRef: Any?,
    property: KProperty<*>
  ) = CodeSnippetExampleDelegate(context.registerExample(property.name))
}


//
//fun <  T> CodeSnippetContext<T>.registeringExample(): CodeSnippetExampleDelegate<T> {
//
//}
//
//
//class CodeSnippetExampleDelegate<T: CodeSnippetContext<*>>(
//  private val context: T
//) {
//  operator fun getValue(context: T, property: KProperty<*>): CodeSnippetExample {
//    return CodeSnippetExample(property.name)
//  }
//}
//
//operator fun  CodeSnippetExample.getValue(
//  thisRef: CodeSnippetExample,
//  property: KProperty<*>,
//): CodeSnippetExample {
//  return CodeSnippetExample(property.name)
//}
//
//operator fun <  T> CodeSnippetContext<T>.provideDelegate(
//  receiver: Any?,
//  property: KProperty<*>
//) = CodeSnippetExampleDelegate<CodeSnippetContext<T>()
