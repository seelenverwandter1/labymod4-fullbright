import gradle.kotlin.dsl.accessors._64acc05bf1a66f2c855e386526b4bcff.api
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.project

fun JavaPluginExtension.javaVersion(javaVersion: JavaVersion) {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

fun DependencyHandler.apiProject(rootProject: Project, name: String, configuration: String? = null) {
  api(project(":${rootProject.name}-$name", configuration))
}