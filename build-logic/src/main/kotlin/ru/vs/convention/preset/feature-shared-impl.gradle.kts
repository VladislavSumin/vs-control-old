package ru.vs.convention.preset

plugins {
    id("ru.vs.convention.preset.feature-shared-api")
    id("ru.vs.convention.serialization.json")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":rsub:core"))
            }
        }
    }
}
