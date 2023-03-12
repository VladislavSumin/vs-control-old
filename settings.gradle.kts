import java.nio.file.Files

rootProject.name = "vs-control"

pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    val coreVersion = extra["ru.vs.control.core_version"].toString()
    resolutionStrategy {
        eachPlugin {
            if (target.id.id.startsWith("ru.vs.convention")) {
                check(target.version == null) { "Wrong convention version: ${target.version}, id: ${target.id.id}" }
                useVersion(coreVersion)
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

val isUseCoreSources = extra["ru.vs.control.use_core_sources"].toString().toBoolean()
if (isUseCoreSources) {
    includeBuild("../vs-core-kt")
}

include(":client:common")
include(":client:android")
include(":client:jvm")