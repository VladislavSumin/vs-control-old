package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all client feature modules
 * Contains basic dependencies and settings typical for client feature
 */

plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    // id("ru.vs.convention.kmp.js") compose (for desktop) don't support js now
    // id("ru.vs.convention.kmp.windows") compose (for desktop) don't support windows now
    // id("ru.vs.convention.kmp.linux") compose (for desktop) don't support linux now
    id("ru.vs.convention.kmp.macos")

    // KMM Test library don't support wasm now
    // id("ru.vs.convention.kmp.wasm")

    id("org.jetbrains.compose")

}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                 implementation(coreLibs.vs.core.compose)
                 implementation(coreLibs.vs.core.coroutines)
                 implementation(coreLibs.vs.core.di)
                 implementation(coreLibs.vs.core.logging)
            }
        }
    }
}