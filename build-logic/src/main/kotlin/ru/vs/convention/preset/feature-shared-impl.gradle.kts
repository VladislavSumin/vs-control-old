package ru.vs.convention.preset

plugins {
    id("ru.vs.convention.preset.feature-shared-api")
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
