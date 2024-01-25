package buildsrc.convention

import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_5
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  id("buildsrc.convention.base")
  kotlin("jvm")
  `java-library`
}

kotlin {
  jvmToolchain(11)
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions {
    freeCompilerArgs.addAll(
      "-opt-in=kotlin.ExperimentalStdlibApi",
      "-opt-in=kotlin.time.ExperimentalTime",
    )
  }
}

tasks.compileTestKotlin {
  compilerOptions {
    freeCompilerArgs.addAll(
      "-opt-in=io.kotest.common.ExperimentalKotest"
    )
  }
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}

java {
  withJavadocJar()
  withSourcesJar()
}
