import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform.CLIENT

plugins {
  id("net.labymod.gradle.vanilla")
  id("net.labymod.gradle.volt")
}

val minecraftGameVersion = "1.19"

java {
  javaVersion(JavaVersion.VERSION_17)
}

dependencies {
  labyProcessor()
  labyApi("v1_19")
  apiProject(rootProject, "core")
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
    compatibilityLevel = "JAVA_17"
    minVersion = "0.8.2"
  }

  packageName("org.burgerbude.labymod.addons.${rootProject.name}.v1_19.mixins")

  version = minecraftGameVersion
}

intellij {
  val javaVersion = project.findProperty("net.labymod.runconfig-v1_19-java-version")

  if (javaVersion != null) {
    run {
      javaVersion(javaVersion as String)
    }
  }
}

tasks {
  collectNatives {
    into("${project.gradle.gradleUserHomeDir}/caches/VanillaGradle/v2/natives/${minecraftGameVersion}/")
  }
}
