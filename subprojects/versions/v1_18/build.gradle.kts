import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform.CLIENT

plugins {
  id("net.labymod.gradle.vanilla")
  id("net.labymod.gradle.volt")
}

val minecraftGameVersion = "1.18.2"

val inheritv117 = sourceSets.inheritFrom(project, "v1_17") {}

dependencies {
  inheritv117.annotationProcessorConfigurationName(labyApi("processor"))

  labyProcessor()
  labyApi("v1_18")
  apiProject(rootProject, "core")

  val project = rootProject.project(":${rootProject.name}-v1_17")
  compileOnly(files(project.sourceSets.main.get().output))
}

java {
  javaVersion(JavaVersion.VERSION_17)
}

projectExt {
  projectName("v1_18")
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

  packageName("org.burgerbude.labymod.addons.${rootProject.name}.v1_17.mixins")
  packageName("org.burgerbude.labymod.addons.${rootProject.name}.v1_18.mixins")

  inheritFrom("v1_17")

  version = minecraftGameVersion
}

intellij {
  val javaVersion = project.findProperty("net.labymod.runconfig-v1_18-java-version")

  if (javaVersion != null) {
    run {
      javaVersion(javaVersion as String)
    }
  }
}

tasks {

  renameApiMixin {
    fun getPackage(name: String): String {
      return "org.burgerbude.labymod.addons.${rootProject.name}.$name.mixins";
    }
    relocate(getPackage("v1_17"), getPackage("v1_18"))
  }

  collectNatives {
    into("${project.gradle.gradleUserHomeDir}/caches/VanillaGradle/v2/natives/${minecraftGameVersion}/")
  }
}
