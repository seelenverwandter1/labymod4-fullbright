buildscript {
  repositories {
    maven("https://dist.labymod.net/api/v1/maven/release/")
    maven("https://repo.spongepowered.org/repository/maven-public")
  }

  dependencies {
    classpath("net.labymod.gradle", "addon", "0.2.42")
  }
}

plugins {
  id("basic-project-convention")
}

apply(plugin = "net.labymod.gradle.addon")

subprojects {
  apply(plugin = "basic-project-convention")
  apply(plugin = "net.labymod.gradle.addon")
}

addon {
  addonInfo {
    namespace(rootProject.name)
    author("BurgerbudeORG")
    description("Example")
    version(project.version.toString())

    internalRelease()
  }
}