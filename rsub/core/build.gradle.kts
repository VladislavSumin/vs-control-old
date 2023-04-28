plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.kotlin.serialization.core)
                implementation(coreLibs.kotlin.serialization.json)
            }
        }
    }
}
