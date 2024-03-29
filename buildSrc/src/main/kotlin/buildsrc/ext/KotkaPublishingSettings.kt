package buildsrc.ext

import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.*


abstract class KotkaPublishingSettings @Inject constructor(
  providers: ProviderFactory,
  objects: ObjectFactory,

  project: Project,
) {

  abstract val mavenPomSubprojectName: Property<String>
  val mavenPomDescription: Property<String> = objects.property<String>().convention(
    providers.provider { project.description }
  )

  val gitHubPackagesCredentials: Provider<Action<PasswordCredentials>> =
    providers.credentialsAction("gitHubPackages")

  val sonatypeRepositoryCredentials: Provider<Action<PasswordCredentials>> =
    providers.credentialsAction("sonatypeRepository")

  val sonatypeRepositoryReleaseUrl: Provider<String> = providers.provider {
    if (project.version.toString().endsWith("SNAPSHOT")) {
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


  companion object {
    const val NAME = "kotkaPublishing"
  }
}
