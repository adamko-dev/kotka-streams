plugins {
  buildsrc.convention.dokkatoo
}

dependencies {
  dokkatoo(projects.modules.kotkaStreamsExtensions)
  dokkatoo(projects.modules.kotkaStreamsFramework)
  dokkatoo(projects.modules.kotkaStreamsKotlinxSerialization)

  dokkatooPluginHtml(libs.kotlin.dokkaPlugin.allModulesPage)
  dokkatooPluginHtml(libs.kotlin.dokkaPlugin.templating)
}

dokkatoo {
  dokkatooPublications.configureEach {
    pluginsConfiguration.create("org.jetbrains.dokka.base.DokkaBase") {
      serializationFormat.set(org.jetbrains.dokka.DokkaConfiguration.SerializationFormat.JSON)
      values.set(
        """
          { 
            "customStyleSheets": [
              "${file("./media/styles/logo-styles.css").invariantSeparatorsPath}" 
            ], 
            "customAssets": [
              "${file("./media/images/logo-icon.svg").invariantSeparatorsPath}"
            ] 
          }
        """.trimIndent()
      )
    }
  }
}

tasks.dokkatooGeneratePublicationHtml {
  inputs.dir("media/styles/")
  inputs.dir("media/images/")
}
