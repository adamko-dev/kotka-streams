package kotka.ext

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugins.signing.SigningExtension


fun MavenPublication.createKotkaStreamsPom(): Unit = pom {
  name.set("Kotka Streams - Kotlin for Kafka Streams")
  description.set("Using Kotka means a more pleasant experience while using Kafka Streams")
  url.set("https://github.com/adamko-dev/kotka-streams")

  licenses {
    license {
      name.set("The Apache License, Version 2.0")
      url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
    }
  }

  developers {
    developer {
      email.set("adam@adamko.dev")
    }
  }

  scm {
    connection.set("scm:git:git://github.com/adamko-dev/kotka-streams.git")
    developerConnection.set("scm:git:ssh://github.com:adamko-dev/kotka-streams.git")
    url.set("https://github.com/adamko-dev/kotka-streams")
  }
}


// hacks because IntelliJ still doesn't properly load DSL accessors for buildSrc


/** Configure [PublishingExtension] */
fun Project.publishing(configure: PublishingExtension.() -> Unit): Unit =
  extensions.configure(configure)


val Project.publishing: PublishingExtension
  get() = extensions.getByType()


/** Configure [SigningExtension] */
fun Project.signing(configure: SigningExtension.() -> Unit): Unit =
  extensions.configure(configure)


val Project.signing: SigningExtension
  get() = extensions.getByType()
