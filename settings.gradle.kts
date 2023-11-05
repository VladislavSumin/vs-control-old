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

plugins {
    id("com.gradle.enterprise") version("3.15.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}

val isUseCoreSources = extra["ru.vs.control.use_core_sources"].toString().toBoolean()
if (isUseCoreSources) {
    includeBuild("../vs-core-kt")
}

include(":core:id")

include(":rsub:core")
include(":rsub:client")
include(":rsub:server")
include(":rsub:ksp:client")
include(":rsub:ksp:server")
include(":rsub:connector:ktor-websocket:core")
include(":rsub:connector:ktor-websocket:client")
include(":rsub:connector:ktor-websocket:server")
include(":rsub:test")
include(":rsub:playground")

includeFeature("about-server")
includeFeature("entities")
includeClientFeature("main-screen")
includeFeature("services")
includeFeature("service-cams-netsurv")
includeFeature("service-debug")

include(":feature:root-navigation:client-api")
include(":feature:root-navigation:client-impl")
include(":feature:servers:client-api")
include(":feature:servers:client-impl")
include(":feature:servers-connection:client-api")
include(":feature:servers-connection:client-impl")

include(":client:common")
include(":client:jvm")
include(":client:android")

if (getBooleanProperty("ru.vs.core.kmp.js.enabled", true)) include(":client:js")

if (getBooleanProperty("ru.vs.core.kmp.iosX64.enabled", true) ||
    getBooleanProperty("ru.vs.core.kmp.iosArm64.enabled", true)
) include(":client:ios")

if (getBooleanProperty("ru.vs.core.kmp.macosX64.enabled", true) ||
    getBooleanProperty("ru.vs.core.kmp.macosArm64.enabled", true)
) include(":client:macos")

include(":server:common")
include(":server:jvm")

/**
 * Include feature modules with default hierarchy
 * add this function call and then run gradle task createFeatureHierarchy to create default configuration
 */
fun includeFeature(name: String) {
    include(":feature:$name:shared-api")
    include(":feature:$name:shared-impl")
    includeClientFeature(name)
    includeServerFeature(name)
}

fun includeClientFeature(name: String) {
    include(":feature:$name:client-api")
    include(":feature:$name:client-impl")
}

fun includeServerFeature(name: String) {
    include(":feature:$name:server-api")
    include(":feature:$name:server-impl")
}

/**
 * We can't use plugins for core-build-logic in settings because its triggers convention evaluation and crash
 * See https://github.com/gradle/gradle/issues/16532
 */
fun getBooleanProperty(name: String, defaultValue: Boolean): Boolean {
    return providers.gradleProperty(name).map { it.toBoolean() }.getOrElse(defaultValue)
}
