package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all client-impl feature modules
 * Contains basic dependencies and settings typical for client feature
 */

plugins {
    id("ru.vs.convention.preset.feature-client-api")
    id("ru.vs.convention.factory-generator")
    id("ru.vs.convention.analyze.compose-report")
    id("ru.vs.convention.compose")
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
                implementation(coreLibs.vs.core.navigation)
                implementation(coreLibs.vs.core.serialization.json)
                implementation(coreLibs.vs.core.uikit.autoSizeText)
                implementation(coreLibs.vs.core.uikit.dropdownMenu)
                implementation(coreLibs.vs.core.uikit.letterAvatar)
                implementation(coreLibs.vs.core.uikit.utils)
                implementation(coreLibs.vs.core.utils)
            }
        }
    }
}
