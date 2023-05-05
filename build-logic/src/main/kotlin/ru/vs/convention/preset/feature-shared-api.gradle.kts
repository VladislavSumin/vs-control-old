package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

plugins {
    id("ru.vs.convention.kmp.all")
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
