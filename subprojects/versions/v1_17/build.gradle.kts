import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform.CLIENT

plugins {
  id("net.labymod.gradle.vanilla")
  id("net.labymod.gradle.volt")
}

val minecraftGameVersion = "1.17.1"

dependencies {
  labyProcessor()
  labyApi("v1_17")
  apiProject(rootProject, "core")
}

java {
  javaVersion(JavaVersion.VERSION_16)
}

minecraft {
  version(minecraftGameVersion)
  platform(CLIENT)
  runs {
    client {
      mainClass("net.minecraft.launchwrapper.Launch")
      args("--tweakClass", "net.labymod.core.loader.vanilla.launchwrapper.LabyModLaunchWrapperTweaker")
      args("--labymod-dev-environment", "true")
      args("--addon-dev-environment", "true")
      jvmArgs("-Dnet.labymod.running-version=$minecraftGameVersion")
    }
  }
}

volt {
  mixin {
    compatibilityLevel = "JAVA_16"
    minVersion = "0.8.2"
  }

  packageName("org.burgerbude.labymod.addons.${rootProject.name}.v1_17.mixins")

  version = minecraftGameVersion
}

intellij {
  val javaVersion = project.findProperty("net.labymod.runconfig-v1_17-java-version")

  if (javaVersion != null) {
    run {
      javaVersion(javaVersion as String)
    }
  }
}

projectExt {
  projectName("v1_17")
}

tasks {
  collectNatives {
    into("${project.gradle.gradleUserHomeDir}/caches/VanillaGradle/v2/natives/${minecraftGameVersion}/")
  }
}
