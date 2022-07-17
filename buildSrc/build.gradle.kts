import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  kotlin("jvm") version "1.6.21"
}

dependencies {

  implementation(enforcedPlatform(libs.kotlin.bom))
  implementation("org.jetbrains.kotlin:kotlin-serialization")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  implementation(libs.kotlin.gradlePlugin)

  implementation(libs.gitVersioningPlugin)
}

val gradleJvmTarget = "1.8"
val gradleJvmVersion = "8"
val gradleKotlinTarget = "1.6"

tasks.withType<KotlinCompile>().configureEach {

  kotlinOptions {
    jvmTarget = gradleJvmTarget
    apiVersion = gradleKotlinTarget
    languageVersion = gradleKotlinTarget
  }

  kotlinOptions.freeCompilerArgs += listOf(
    "-opt-in=kotlin.RequiresOptIn",
    "-opt-in=kotlin.ExperimentalStdlibApi",
    "-opt-in=kotlin.time.ExperimentalTime",
  )
}

kotlin {
  jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(gradleJvmVersion))
  }

  kotlinDslPluginOptions {
    jvmTarget.set(gradleJvmTarget)
  }
}
