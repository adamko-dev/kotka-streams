package kotka.convention.release

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
  }
}
