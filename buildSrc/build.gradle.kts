import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  kotlin("jvm") version "1.6.10"
}

dependencies {

  val kotlinVersion = "1.6.10"
  implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
  implementation("org.jetbrains.kotlin:kotlin-serialization")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

  val kotlinXSerializationVersion = "1.3.2"
  implementation(enforcedPlatform("org.jetbrains.kotlinx:kotlinx-serialization-bom:$kotlinXSerializationVersion"))

  val gitVersioningPluginVersion = "5.1.5"
  implementation("me.qoomon:gradle-git-versioning-plugin:$gitVersioningPluginVersion")

  val kotlinxKoverVersion = "0.5.0"
  implementation("org.jetbrains.kotlinx:kover:${kotlinxKoverVersion}")
}

val projectJvmTarget = "11"

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

kotlin {
  jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(projectJvmTarget))
  }

  kotlinDslPluginOptions {
    jvmTarget.set(projectJvmTarget)
  }
}
