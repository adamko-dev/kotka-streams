import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  kotlin("jvm") version "1.6.21"
}

dependencies {

  val kotlinVersion = libs.versions.kotlin.get()
  implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
  implementation("org.jetbrains.kotlin:kotlin-serialization")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

  val kotlinXSerializationVersion = libs.versions.kotlinx.serialization.get()
  implementation(enforcedPlatform("org.jetbrains.kotlinx:kotlinx-serialization-bom:$kotlinXSerializationVersion"))

  val gitVersioningPluginVersion = "5.2.0"
  implementation("me.qoomon:gradle-git-versioning-plugin:$gitVersioningPluginVersion")
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
//    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
//    "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
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
