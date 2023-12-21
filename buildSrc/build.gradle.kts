plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(libs.gradlePlugin.kotlin)
  implementation(libs.gradlePlugin.kotlinxSerialization)

  implementation(libs.gradlePlugin.dokkatoo)

  // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

kotlin {
  jvmToolchain(11)
}
