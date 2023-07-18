package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all server-api feature modules
 * Contains basic dependencies and settings typical for sever feature
 */

plugins {
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.linux")
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
