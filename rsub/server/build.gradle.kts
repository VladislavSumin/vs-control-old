plugins {
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.linux")
    id("ru.vs.convention.kmp.macos")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)

                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.logging)
                implementation(coreLibs.kotlin.serialization.core)
                implementation(coreLibs.kotlin.serialization.json)
            }
        }
    }
}
