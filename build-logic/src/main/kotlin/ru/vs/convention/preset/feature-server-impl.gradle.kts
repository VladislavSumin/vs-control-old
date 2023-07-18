package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all server-impl feature modules
 * Contains basic dependencies and settings typical for sever feature
 */

plugins {
    id("ru.vs.convention.preset.feature-server-api")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.di)
                implementation(coreLibs.vs.core.logging)
                implementation(coreLibs.vs.core.network)
                implementation(coreLibs.vs.core.utils)
            }
        }
    }
}
