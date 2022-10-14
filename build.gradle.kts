buildscript {
  repositories {
    maven("https://dist.labymod.net/api/v1/maven/release/")
    maven("https://repo.spongepowered.org/repository/maven-public")
  }

  dependencies {
    classpath("net.labymod.gradle", "addon", "0.2.46")
  }
}

plugins {
  id("basic-project-convention")
  id("org.cadixdev.licenser") version ("0.6.1")
}

apply(plugin = "net.labymod.gradle.addon")

subprojects {
  apply(plugin = "basic-project-convention")
  apply(plugin = "net.labymod.gradle.addon")
  apply(plugin = "org.cadixdev.licenser")

  license {
    header(rootProject.file("LICENSE-HEADER.txt"))
    newLine.set(true)
  }

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