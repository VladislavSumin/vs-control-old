package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all client-impl feature modules
 * Contains basic dependencies and settings typical for client feature
 */

plugins {
    id("ru.vs.convention.preset.feature-client-api")
    id("org.jetbrains.compose")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.compose)
                implementation(coreLibs.vs.core.di)
                implementation(coreLibs.vs.core.keyValueStorage)
                implementation(coreLibs.vs.core.ktor.client)
                implementation(coreLibs.vs.core.logging)
                implementation(coreLibs.vs.core.mvi)
                implementation(coreLibs.vs.core.serialization.json)
                implementation(coreLibs.vs.core.uikit.dropdownMenu)
                implementation(coreLibs.vs.core.uikit.localConfiguration)
                implementation(coreLibs.vs.core.utils)
            }
        }
    }
}
