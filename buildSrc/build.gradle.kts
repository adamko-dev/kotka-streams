plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(libs.gradlePlugin.kotlin)
  implementation(libs.gradlePlugin.kotlinxSerialization)

  implementation(libs.gradlePlugin.dokkatoo)

  // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

  val kotlinxKoverVersion = "0.5.0"
  implementation("org.jetbrains.kotlinx:kover:${kotlinxKoverVersion}")

  val kotlinxKnitVersion = "0.3.0"
  implementation("org.jetbrains.kotlinx:kotlinx-knit:${kotlinxKnitVersion}")

  implementation("dev.jacomet.gradle.plugins:logging-capabilities:0.10.0")
}

kotlin {
  jvmToolchain(11)
}
