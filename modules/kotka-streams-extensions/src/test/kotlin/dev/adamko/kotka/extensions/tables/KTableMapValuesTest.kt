package dev.adamko.kotka.extensions.tables

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.apache.kafka.streams.kstream.KTable
import org.apache.kafka.streams.kstream.ValueMapperWithKey

class KTableMapValuesTest : BehaviorSpec({

  Given("a KTable") {
    val mockKTable: KTable<String, String> = mockk(relaxed = true) {}

    When("mapValues, mapper only") {
      mockKTable.mapValues(mapper = { key, value -> "dummy mapper $key $value" })
      Then("expect MapValues method is called") {
        verify(exactly = 1) { mockKTable.mapValues(any<ValueMapperWithKey<String, String, Any>>()) }
        confirmVerified(mockKTable)
      }
    }
  }

})
