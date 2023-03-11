dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }

}

includeBuild("../../vs-core-kt/core-build-logic")