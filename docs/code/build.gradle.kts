plugins {
  kotka.convention.`kotlin-jvm`
  id("org.jetbrains.kotlinx.knit")
}


dependencies {
//  implementation(projects.modules.kxsTsGenCore)

  implementation("org.jetbrains.kotlinx:kotlinx-knit:0.3.0")

  testImplementation(kotlin("test"))

  testImplementation("org.jetbrains.kotlinx:kotlinx-knit-test:0.3.0")
}


sourceSets.test {
  java.srcDirs(
    layout.projectDirectory.dir("example"),
    layout.projectDirectory.dir("test"),
  )
}

knit {
  val docsDir = rootProject.layout.projectDirectory.dir("docs")
  rootDir = docsDir.asFile
  files = project.fileTree(docsDir) {
    include("*.md")
  }
}

tasks.test {
  dependsOn(tasks.knit)
//  finalizedBy(tasks.knitCheck)
}

tasks.compileKotlin { mustRunAfter(tasks.knit) }

//tasks.knitCheck {
//  dependsOn(tasks.test)
//}
