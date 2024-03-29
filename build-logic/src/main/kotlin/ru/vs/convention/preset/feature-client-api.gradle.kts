package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all client-api feature modules
 * Contains basic dependencies and settings typical for client feature
 */

plugins {
    id("ru.vs.convention.kmp.android-library")
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.ios")
    id("ru.vs.convention.kmp.js")
    // id("ru.vs.convention.kmp.windows") compose (for desktop) don't support windows now
    // id("ru.vs.convention.kmp.linux") compose (for desktop) don't support linux now
    id("ru.vs.convention.kmp.macos")

    // KMM Test library don't support wasm now
    // id("ru.vs.convention.kmp.wasm")

    id("kotlin-parcelize")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.decompose)
                implementation(coreLibs.essenty.parcelable)
            }
        }
    }
}
