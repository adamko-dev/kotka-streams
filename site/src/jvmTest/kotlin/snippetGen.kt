package dev.adamko.kotka.site

import kotlin.test.Test
import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import kotlinx.html.org.w3c.dom.events.Event

class SnippetGenTest {

  @Test
  fun generateSnippets() {
    val result = SnippetGenerator.namingOperators2()
  }

}

private object SnippetGenerator : TagConsumer<List<String>> {
  override fun finalize(): List<String> {

  }

  override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {

  }

  override fun onTagComment(content: CharSequence) {

  }

  override fun onTagContent(content: CharSequence) {

  }

  override fun onTagContentEntity(entity: Entities) {

  }

  override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {

  }

  override fun onTagEnd(tag: Tag) {

  }

  override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {

  }

  override fun onTagStart(tag: Tag) {
    tag.attributes
  }
}
