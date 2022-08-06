package dev.adamko.kotka.extensions.streams

import io.kotest.core.spec.style.FunSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.apache.kafka.streams.kstream.Branched
import org.apache.kafka.streams.kstream.BranchedKStream
import org.apache.kafka.streams.kstream.Predicate

class BranchedKStreamTest : FunSpec({

  context(".branch() extension") {

    val branchedKStream: BranchedKStream<String, String> = mockk {
      every { branch(any(), any()) } returns mockk()
    }

    test("verify extension calls actual function") {

      branchedKStream.branch(
        branched = mockk(),
      ) { k, v ->
        k == v
      }

      verify(exactly = 1) {
        branchedKStream.branch(
          any<Predicate<in String, in String>>(),
          any<Branched<String, String>>(),
        ) }

      confirmVerified(branchedKStream)
    }
  }
})
