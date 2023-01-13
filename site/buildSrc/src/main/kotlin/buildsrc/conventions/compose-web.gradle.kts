package buildsrc.conventions


plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

kotlin {
  js(IR)

  jvm()
}
