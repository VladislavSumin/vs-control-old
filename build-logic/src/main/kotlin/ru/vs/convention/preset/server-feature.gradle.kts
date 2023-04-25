package ru.vs.convention.preset

import org.gradle.accessors.dm.LibrariesForCoreLibs

/**
 * Basic preset for all server feature modules
 * Contains basic dependencies and settings typical for sever feature
 */

plugins {
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.linux")
    id("ru.vs.convention.kmp.macos")
}

val coreLibs = rootProject.the<LibrariesForCoreLibs>()

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.di)
                implementation(coreLibs.vs.core.ktor.server)
                implementation(coreLibs.vs.core.logging)
                implementation(coreLibs.vs.core.utils)
            }
        }
    }
}