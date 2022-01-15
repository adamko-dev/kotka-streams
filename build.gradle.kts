plugins {
  base
  `project-report`
  `build-dashboard`
  kotka.convention.lang.`kotlin-jvm`
  kotka.convention.release.`maven-publish`
  kotka.convention.versioning
}

group = "dev.adamko.kotka"
// NOTE: version is configured by `kotka.convention.versioning` plugin
version = "0.0.0-SNAPSHOT"

dependencies {
  api(projects.modules.kotkaStreamsTopicData)
  api(projects.modules.kotkaStreamsExtensions)
  api(projects.modules.kotkaStreamsKotlinxSerialization)
}

tasks.wrapper {
  gradleVersion = "7.3.3"
  distributionType = Wrapper.DistributionType.ALL
}
