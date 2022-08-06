package buildsrc.ext

import org.gradle.api.Action
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory


// https://github.com/gradle/gradle/issues/20925
fun ProviderFactory.credentialsAction(
  repositoryName: String
): Provider<Action<PasswordCredentials>> = zip(
  gradleProperty("${repositoryName}Username"),
  gradleProperty("${repositoryName}Password"),
) { user, pass ->
  Action<PasswordCredentials> {
    username = user
    password = pass
  }
}
