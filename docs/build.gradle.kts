import dev.adamko.dokkatoo.dokka.plugins.DokkaHtmlPluginParameters
import dev.adamko.dokkatoo.dokka.plugins.DokkaHtmlPluginParameters.Companion.DOKKA_HTML_PARAMETERS_NAME

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
  moduleName.set("Kotka Streams")

  pluginsConfiguration.named<DokkaHtmlPluginParameters>(DOKKA_HTML_PARAMETERS_NAME) {
    customStyleSheets.from("./styles/logo-styles.css")
    customAssets.from("./images/logo-icon.svg")
  }
}
