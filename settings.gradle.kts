rootProject.name = "fullbright"

includeBuild("laby-build-logic")

pluginManagement {
  val labyGradlePluginVersion = "0.3.25"
  plugins {
    id("net.labymod.gradle") version (labyGradlePluginVersion)
  }

  buildscript {
    repositories {
      maven("https://dist.labymod.net/api/v1/maven/release/")
      maven("https://repo.spongepowered.org/repository/maven-public")
      mavenCentral()
      mavenLocal()
    }

    dependencies {
      classpath("net.labymod.gradle", "addon", labyGradlePluginVersion)
    }
  }
}

plugins.apply("net.labymod.gradle")

sequenceOf("api", "core").forEach {
  includeProject(it)
}

fun includeProject(path: String) {
  var projectPath = path;
  if (projectPath.contains(":")) {
    projectPath = projectPath.substring(projectPath.lastIndexOf(':') + 1, projectPath.length)
  }

  include(projectPath)
  findProject(":$projectPath")?.apply {
    projectDir = file("subprojects/${path.replace(":", "/")}")
    if (projectDir.exists().not()) {
      projectDir.mkdirs()
    }


    name = rootProject.name + "-" + projectPath
  }
}
