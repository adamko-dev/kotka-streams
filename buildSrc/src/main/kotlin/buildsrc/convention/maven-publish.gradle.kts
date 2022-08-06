package buildsrc.convention

import buildsrc.ext.KotkaPublishingSettings
import buildsrc.ext.createKotkaStreamsPom
import buildsrc.ext.gavString


plugins {
  `maven-publish`
  signing
}


val kotkaPublishingSettings: KotkaPublishingSettings =
  extensions.create(
    KotkaPublishingSettings.NAME,
    KotkaPublishingSettings::class,
    project,
  )


tasks.withType<AbstractPublishToMaven>().configureEach {
  // Gradle warns about some signing tasks using publishing task outputs without explicit
  // dependencies. I'm not going to go through them all and fix them, so here's a quick fix.
  dependsOn(tasks.withType<Sign>())

  doLast {
    logger.lifecycle("[${this.name}] ${publication.gavString}")
  }
}


// signing must be configured *after* publications are created
fun Project.configureSigning() = signing {
  with(kotkaPublishingSettings) {
    if (signingKeyId.isPresent && signingKey.isPresent && signingPassword.isPresent) {
      useInMemoryPgpKeys(signingKeyId.get(), signingKey.get(), signingPassword.get())
      // sign all publications
      sign(publishing.publications)
    }
  }
}


publishing {
  repositories {
    // publish to local dir, for testing
    maven(rootProject.layout.buildDirectory.dir("maven-internal")) {
      name = "ProjectLocalDir"
    }

    with(kotkaPublishingSettings) {
      if (sonatypeRepositoryCredentials.isPresent) {
        maven(sonatypeRepositoryReleaseUrl) {
          name = "Sonatype"
          credentials(sonatypeRepositoryCredentials.get())
        }
      }

      if (gitHubPackagesCredentials.isPresent) {
        maven("https://maven.pkg.github.com/adamko-dev/kotka-streams") {
          name = "GitHubPackages"
          credentials(gitHubPackagesCredentials.get())
        }
      }
    }
  }

  publications.withType<MavenPublication>().configureEach {
    createKotkaStreamsPom {
      name.set(kotkaPublishingSettings.mavenPomSubprojectName.map {
        "Kotka Streams :: $it"
      })
      description.set(kotkaPublishingSettings.mavenPomDescription)
    }
  }
}


plugins.withType<JavaPlugin>().configureEach {
  publishing.publications.create<MavenPublication>("mavenJava") {
    from(components["java"])
  }

  configureSigning()
}


plugins.withType<JavaPlatformPlugin>().configureEach {

  // Javadoc might not be necesary as the 'pom packaging' is 'pom'?
//  val javadocJarStub by tasks.registering(Jar::class) {
//    group = JavaBasePlugin.DOCUMENTATION_GROUP
//    description = "Stub javadoc.jar artifact (required by Maven Central)"
//    archiveClassifier.set("javadoc")
//  }

  publishing.publications.create<MavenPublication>("mavenJavaPlatform") {
    from(components["javaPlatform"])
//    artifact(javadocJarStub)
  }

  configureSigning()
}

// workaround for https://github.com/gradle/gradle/issues/16543
inline fun <reified T : Task> TaskContainer.provider(taskName: String): Provider<T> =
  providers.provider { taskName }
    .flatMap { named<T>(it) }
