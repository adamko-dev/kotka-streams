package kotka.convention

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("kotka.convention.subproject")
  kotlin("jvm")
  `java-library`
}


dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
    because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions")
  }

  testImplementation("io.kotest:kotest-runner-junit5")
  testImplementation("io.kotest:kotest-assertions-core")
  testImplementation("io.kotest:kotest-property")
  testImplementation("io.kotest:kotest-assertions-json")

  testImplementation("io.mockk:mockk")
}


val projectJvmTarget = "1.8"
val projectJvmVersion = "8"
val projectKotlinTarget = "1.6"


kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(projectJvmVersion))
  }
}


tasks.withType<KotlinCompile>().configureEach {

  kotlinOptions {
    jvmTarget = projectJvmTarget
    apiVersion = projectKotlinTarget
    languageVersion = projectKotlinTarget
  }

  kotlinOptions.freeCompilerArgs += listOf(
    "-opt-in=kotlin.RequiresOptIn",
    "-opt-in=kotlin.ExperimentalStdlibApi",
    "-opt-in=kotlin.time.ExperimentalTime",
//    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
//    "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
  )
}


tasks.compileTestKotlin {
  kotlinOptions.freeCompilerArgs += "-opt-in=io.kotest.common.ExperimentalKotest"
}


tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}


java {
  withJavadocJar()
  withSourcesJar()
}
