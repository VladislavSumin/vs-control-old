enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "vs-control"

pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
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
    // KMP ios add custom repositories
    // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }

    versionCatalogs {
        // TODO add support for isUseCoreSources flag
        create("coreLibs") {
            from(files("../vs-core-kt/core-libs.versions.toml"))
        }
    }
}

val isUseCoreSources = extra["ru.vs.control.use_core_sources"].toString().toBoolean()
if (isUseCoreSources) {
    includeBuild("../vs-core-kt")
}

include(":client:common")
include(":client:android")
include(":client:js")
include(":client:jvm")

include(":server:common")
include(":server:jvm")
