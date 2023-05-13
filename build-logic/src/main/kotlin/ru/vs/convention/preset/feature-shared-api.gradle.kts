package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.coroutines)
            }
        }
    }
}
