package kotka.convention

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("kotka.convention.subproject")
  kotlin("jvm")
  `java-library`
}
dependencies {

  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

//  val junitVersion = "5.8.2"
//  testImplementation(platform("org.junit:junit-bom:$junitVersion"))
//  testImplementation("org.junit.jupiter:junit-jupiter")
//  testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
//    because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions")
//  }

  val kotestVersion = "5.1.0"
  testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
  testImplementation("io.kotest:kotest-runner-junit5")
  testImplementation("io.kotest:kotest-assertions-core")
  testImplementation("io.kotest:kotest-property")
  testImplementation("io.kotest:kotest-assertions-json")

  testImplementation("io.mockk:mockk:1.12.3")

}

val projectJvmTarget = "11"

kotlin {
  jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(projectJvmTarget))
  }
}

tasks.withType<KotlinCompile>().configureEach {

  kotlinOptions {
    jvmTarget = projectJvmTarget
    apiVersion = "1.6"
    languageVersion = "1.6"
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

tasks.withType<Test> {
  useJUnitPlatform()
}

java {
  withJavadocJar()
  withSourcesJar()
}
