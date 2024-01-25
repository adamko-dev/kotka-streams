package dev.adamko.kotka.extensions.tables

import dev.adamko.kotka.extensions.materializedAs
import dev.adamko.kotka.extensions.namedAs
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.apache.kafka.streams.StreamsBuilder

class KTableExtensionsTests : FunSpec({

  context(".mapValues()") {

    context("expect extension function produces same topology as wrapped function") {

      test("name is present, materialized is null") {

        val originalBuilder = StreamsBuilder()
        originalBuilder
          .table<String, String>("table-topic")
          .mapValues(
            { readOnlyKey, value -> "$readOnlyKey, $value" },
            namedAs("map-values-test"),
          )
        val describeOriginal = originalBuilder.build().describe()


        val extensionBuilder = StreamsBuilder()
        extensionBuilder
          .table<String, String>("table-topic")
          .mapValues(name = "blah", materialized = null) { readOnlyKey, value ->
            "$readOnlyKey, $value"
          }
        val describeExtension = extensionBuilder.build().describe()

        describeExtension shouldBe describeOriginal
      }


      test("name is present, materialized is present") {

        val originalBuilder = StreamsBuilder()
        originalBuilder
          .table<String, String>("table-topic")
          .mapValues(
            { readOnlyKey, value -> "$readOnlyKey, $value" },
            namedAs("map-values-test"),
            materializedAs("store-name"),
          )
        val describeOriginal = originalBuilder.build().describe()


        val extensionBuilder = StreamsBuilder()
        extensionBuilder
          .table<String, String>("table-topic")
          .mapValues(name = "blah", materializedAs("store-name")) { readOnlyKey, value ->
            "$readOnlyKey, $value"
          }
        val describeExtension = extensionBuilder.build().describe()

        describeExtension shouldBe describeOriginal
      }


      test("name is null, materialized is present") {

        val originalBuilder = StreamsBuilder()
        originalBuilder
          .table<String, String>("table-topic")
          .mapValues(
            { readOnlyKey, value -> "$readOnlyKey, $value" },
            materializedAs("store-name"),
          )
        val describeOriginal = originalBuilder.build().describe()


        val extensionBuilder = StreamsBuilder()
        extensionBuilder
          .table<String, String>("table-topic")
          .mapValues(name = null, materializedAs("store-name")) { readOnlyKey, value ->
            "$readOnlyKey, $value"
          }
        val describeExtension = extensionBuilder.build().describe()

        describeExtension shouldBe describeOriginal
      }


      test("name is null, materialized is null") {

        val originalBuilder = StreamsBuilder()
        originalBuilder
          .table<String, String>("table-topic")
          .mapValues { readOnlyKey, value -> "$readOnlyKey, $value" }
        val describeOriginal = originalBuilder.build().describe()


        val extensionBuilder = StreamsBuilder()
        extensionBuilder
          .table<String, String>("table-topic")
          .mapValues(name = null, materialized = null) { readOnlyKey, value ->
            "$readOnlyKey, $value"
          }
        val describeExtension = extensionBuilder.build().describe()

        describeExtension shouldBe describeOriginal
      }
    }
  }
})
