plugins {
  buildsrc.convention.subproject
  buildsrc.convention.`maven-publish`

  `java-platform`
}

javaPlatform {
  allowDependencies()
}

dependencies {

  api(platform(libs.kotlin.bom))
  api(platform(libs.kotlinxSerialization.bom))

  api(platform(libs.kotest.bom))
  api(platform(libs.junit.bom))

  constraints {
    api(libs.kafka.streams)
    api(libs.kotlinx.knitTest)
    api(libs.mockk)
  }
}
