plugins {
  buildsrc.convention.base
  buildsrc.convention.`maven-publish`

  `java-platform`
}

description = "Aligns versions of project dependencies"

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
    api(libs.slf4j.api)
    api(libs.slf4j.simple)
  }
}

kotkaPublishing {
  mavenPomSubprojectName.set("Versions Platform")
}
