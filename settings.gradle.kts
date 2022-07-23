rootProject.name = "addon-template"

includeBuild("build-logic")

sequenceOf("api", "core").forEach {
  includeProject(it)
}

sequenceOf("8", "17", "18", "19").forEach {
  includeProject("versions:v1_$it")
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
