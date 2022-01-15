package kotka.convention

plugins {
  `maven-publish`
}

plugins.withType(JavaPlugin::class.java) {
  publishing {
    publications {
      create<MavenPublication>("mavenJava") {
        from(components["java"])
      }
    }
    repositories {
      maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/adamko-dev/kotka-streams")
        credentials(PasswordCredentials::class)
      }
    }
  }
}
