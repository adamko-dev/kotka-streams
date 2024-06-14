<#-- @ftlvariable name="test.name" type="java.lang.String" -->
<#-- @ftlvariable name="test.package" type="java.lang.String" -->
<#-- @ftlvariable name="test.language" type="java.lang.String" -->
// Knit tool automatically generated this file from ${file.name}. Do not edit.
@file:Suppress("JSUnusedLocalSymbols")
package ${test.package}

import io.kotest.matchers.*
import kotlinx.knit.test.*
import org.junit.jupiter.api.Test

class ${test.name} {
<#list cases as case><#assign method = test["mode.${case.param}"]!"custom">
  @Test
  fun test${case.name}() {

    // language=${test.language}
    val expected = """
    <#list case.lines as line>
    |${line}
    </#list>
    """.trimMargin().trim()

    val output = captureOutput("${case.name}") {
      ${case.knit.package}.${case.knit.name}.main()
    }
      .joinToString("\n")

    val kafkaStreamDescription = output
      .substringAfter("kafkaStreamBuilder described:", "")
      .substringBefore("~~~~~~", missingDelimiterValue = "")
      .trim()
    val kotkaStreamDescription = output
      .substringAfter("kotkaStreamBuilder described:", "")
      .substringBefore("~~~~~~", missingDelimiterValue = "")
      .trim()

    kafkaStreamDescription.shouldBe(expected)
    kafkaStreamDescription.shouldBe(expected)
    kafkaStreamDescription.shouldBe(kotkaStreamDescription)

<#--    <#if method != "custom">.${method}(-->
<#--        // language=${test.language}-->
<#--        """-->
<#--        """-->
<#--      )-->
<#--<#else>.also { lines ->-->
<#--      check(${case.param})-->
<#--    }-->
<#--</#if>-->
  }
<#sep>

</#list>
}
