package ru.vs.convention.preset

/**
 * Default setup for entities-factory-generator
 */

plugins {
    id("kotlin-multiplatform")
    id("ru.vs.convention.ksp-kmp-hack")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":feature:entities:factory-generator-api"))
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":feature:entities:factory-generator-ksp"))
}
