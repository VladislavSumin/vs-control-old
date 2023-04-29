plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.coroutines)
            }
        }
    }
}
