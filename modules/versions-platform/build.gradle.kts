plugins {
  buildsrc.convention.subproject
  buildsrc.convention.`maven-publish`

  `java-platform`
}

description = "Aligns versions of project dependencies"


javaPlatform {
  allowDependencies()
}


dependencies {
  api(platform(libs.kotlin.bom))
  api(platform(libs.kotlinxSerialization.bom))

  api(platform(libs.kotest.bom))
  api(platform(libs.junit.bom))

  constraints {
    api(libs.kafka.streams)
    api(libs.kotlinx.knitTest)
    api(libs.mockk)
  }
}


//val javadocJarStub by tasks.registering(Jar::class) {
//  group = JavaBasePlugin.DOCUMENTATION_GROUP
//  description = "Stub javadoc.jar artifact (required by Maven Central)"
//  archiveClassifier.set("javadoc")
//}

//publishing.publications.create<MavenPublication>("mavenJavaPlatform") {
//  from(components["javaPlatform"])
//  artifact(javadocJarStub)
//}
