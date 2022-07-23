plugins {
  id("net.labymod.gradle.legacyminecraft")
  id("net.labymod.gradle.volt")
}

val minecraftGameVersion = "1.8.9"

dependencies {
  labyProcessor()
  labyApi("v1_8")
  apiProject(rootProject, "core")
}

java {
  javaVersion(JavaVersion.VERSION_1_8)
}

legacyMinecraft {
  version(minecraftGameVersion)
  mainClass("net.minecraft.launchwrapper.Launch")
  args("--tweakClass", "net.labymod.core.loader.vanilla.launchwrapper.LabyModLaunchWrapperTweaker")
  args("--labymod-dev-environment", "true")
  args("--addon-dev-environment", "true")
  jvmArgs("-Dnet.labymod.running-version=$minecraftGameVersion")
}

volt {
  mixin {
    compatibilityLevel = "JAVA_8"
    minVersion = "0.6.6"
  }

  packageName("org.burgerbude.labymod.addons.${rootProject.name}.v1_8.mixins")

  version = minecraftGameVersion
}

intellij {
  val javaVersion = project.findProperty("net.labymod.runconfig-v1_8-java-version")

  if (javaVersion != null) {
    run {
      javaVersion(javaVersion as String)
    }
  }
}
