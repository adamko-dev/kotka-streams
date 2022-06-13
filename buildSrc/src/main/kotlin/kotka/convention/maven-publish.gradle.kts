package kotka.convention

import kotka.ext.createKotkaStreamsPom
import kotka.ext.credentialsAction
import kotka.ext.publishing
import kotka.ext.signing


plugins {
  `maven-publish`
  signing
}


val sonatypeRepositoryCredentials: Provider<Action<PasswordCredentials>> =
  providers.credentialsAction("sonatypeRepository")

val gitHubPackagesCredentials: Provider<Action<PasswordCredentials>> =
  providers.credentialsAction("GitHubPackages")


val sonatypeRepositoryReleaseUrl: Provider<String> = provider {
  if (version.toString().endsWith("SNAPSHOT")) {
    "https://s01.oss.sonatype.org/content/repositories/snapshots/"
  } else {
    "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
  }
}


val signingKeyId: Provider<String> =
  providers.gradleProperty("signing.keyId")
val signingKey: Provider<String> =
  providers.gradleProperty("signing.key")
val signingPassword: Provider<String> =
  providers.gradleProperty("signing.password")
val signingSecretKeyRingFile: Provider<String> =
  providers.gradleProperty("signing.secretKeyRingFile")


tasks.withType<AbstractPublishToMaven>().configureEach {
  // Gradle warns about some signing tasks using publishing task outputs without explicit
  // dependencies. I'm not going to go through them all and fix them, so here's a quick fix.
  dependsOn(tasks.withType<Sign>())

  doLast {
    logger.lifecycle("[${this.name}] ${project.group}:${project.name}:${project.version}")
  }
}


signing {
  if (sonatypeRepositoryCredentials.isPresent) {
    if (signingKeyId.isPresent && signingKey.isPresent && signingPassword.isPresent) {
      useInMemoryPgpKeys(signingKeyId.get(), signingKey.get(), signingPassword.get())
    } else {
      useGpgCmd()
    }

    // sign all publications
    sign(publishing.publications)
  }
}


publishing {
  repositories {
    // publish to local dir, for testing
    maven(rootProject.layout.buildDirectory.dir("maven-internal")) {
      name = "maven-internal"
    }

//    if (sonatypeRepositoryCredentials.isPresent) {
//      maven(sonatypeRepositoryReleaseUrl) {
//        name = "sonatype"
//        credentials(sonatypeRepositoryCredentials.get())
//      }
//    }
//
//    if (gitHubPackagesCredentials.isPresent) {
//      maven("https://maven.pkg.github.com/adamko-dev/kotka-streams") {
//        name = "GitHubPackages"
//        credentials(gitHubPackagesCredentials.get())
//      }
//    }
  }

  publications.withType<MavenPublication>().configureEach {
    createKotkaStreamsPom()
  }
}


plugins.withType<JavaPlugin>().configureEach {
  publishing.publications.create<MavenPublication>("mavenJava") {
    from(components["java"])
//    artifact(tasks["sourcesJar"])
  }
}


plugins.withType<JavaPlatformPlugin>().configureEach {

  val javadocJarStub by tasks.registering(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Stub javadoc.jar artifact (required by Maven Central)"
    archiveClassifier.set("javadoc")
  }

  tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(javadocJarStub)
  }

  if (sonatypeRepositoryCredentials.isPresent) {
    signing.sign(javadocJarStub.get())
  }

  publishing.publications.create<MavenPublication>("mavenJavaPlatform") {
    from(components["javaPlatform"])
    artifact(javadocJarStub)
  }
}
