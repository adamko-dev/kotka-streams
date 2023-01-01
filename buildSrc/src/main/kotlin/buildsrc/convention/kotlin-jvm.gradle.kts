package buildsrc.convention

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  id("buildsrc.convention.subproject")
  kotlin("jvm")
  `java-library`
  id("org.jetbrains.dokka")
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


kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(11))
  }
}


tasks.withType<KotlinCompile>().configureEach {

  kotlinOptions {
    apiVersion = "1.5"
    languageVersion = "1.8"
  }

  kotlinOptions.freeCompilerArgs += listOf(
    "-opt-in=kotlin.RequiresOptIn",
    "-opt-in=kotlin.ExperimentalStdlibApi",
    "-opt-in=kotlin.time.ExperimentalTime",
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


tasks.withType<Jar>().named("javadocJar") {
  from(tasks.dokkaHtml)
}
