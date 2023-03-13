package dev.adamko.kotka.site.snippets


class SnippetDoc(
  val title: String
) {



}

fun snippetDoc(
  title: String,
  content: SnippetDoc.() -> Unit,
) {
  val doc = SnippetDoc(title)
  doc.content()
}
