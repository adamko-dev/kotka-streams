import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(libs.gradlePlugin.kotlin)
  implementation(libs.gradlePlugin.kotlinxSerialization)

  implementation(libs.gradlePlugin.gitVersioning)

  implementation(libs.kotlin.dokkaCore)
  implementation(libs.gradlePlugin.dokkatoo)

  // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
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
