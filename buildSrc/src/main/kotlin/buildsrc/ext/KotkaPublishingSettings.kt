package buildsrc.ext

import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.tasks.Input


abstract class KotkaPublishingSettings @Inject constructor(
  providers: ProviderFactory,

  project: Project,
) {

  @get:Input
  abstract val mvnPomConfig: Property<Action<MavenPom>>

  val gitHubPackagesCredentials: Provider<Action<PasswordCredentials>> =
    providers.credentialsAction("GitHubPackages")


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
  val signingSecretKeyRingFile: Provider<String> =
    providers.gradleProperty("signing.secretKeyRingFile")


  companion object {
    const val NAME = "kotkaPublishing"
  }
}
