package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all client feature modules
 * Contains basic dependencies and settings typical for client feature
 */

plugins {
    id("ru.vs.convention.kmp.all")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                 implementation(coreLibs.vs.core.coroutines)
                 implementation(coreLibs.vs.core.di)
                 implementation(coreLibs.vs.core.logging)
            }
        }
    }
}