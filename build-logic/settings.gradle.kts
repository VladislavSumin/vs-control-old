dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }

}

rootProject.name = "build-logic"

val isUseCoreSources = extra["ru.vs.control.use_core_sources"].toString().toBoolean()
if (isUseCoreSources) {
    includeBuild("../../vs-core-kt/core-build-logic")
}