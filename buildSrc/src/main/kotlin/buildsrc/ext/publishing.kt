package buildsrc.ext

import org.gradle.api.Action
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication


fun MavenPublication.createKotkaStreamsPom(
  configure: Action<MavenPom>? = null
): Unit = pom {
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

  if (configure != null) {
    configure.execute(this)
  }
}


/** `group:artifact:version` string */
val MavenPublication.gavString: String get() = "$groupId:$artifactId:$version"
