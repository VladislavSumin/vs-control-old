package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

plugins {
    id("ru.vs.convention.preset.feature-shared-api")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.di)
                implementation(project(":rsub:core"))
            }
        }
    }
}
