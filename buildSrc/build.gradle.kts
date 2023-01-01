import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  kotlin("jvm") version embeddedKotlinVersion
}

dependencies {
  implementation(libs.gradlePlugin.kotlin)
  implementation(libs.gradlePlugin.kotlinxSerialization)

  implementation(libs.gradlePlugin.gitVersioning)
  implementation(libs.gradlePlugin.dokka)
}

val gradleJvmTarget = "11"
val gradleJvmVersion = "11"

tasks.withType<KotlinCompile>().configureEach {

  kotlinOptions {
    jvmTarget = gradleJvmTarget
  }

  kotlinOptions.freeCompilerArgs += listOf(
    "-opt-in=kotlin.RequiresOptIn",
    "-opt-in=kotlin.ExperimentalStdlibApi",
    "-opt-in=kotlin.time.ExperimentalTime",
  )
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(gradleJvmVersion))
  }

  kotlinDslPluginOptions {
    jvmTarget.set(gradleJvmTarget)
  }
}
