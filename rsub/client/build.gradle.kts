plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
    id("kotlinx-atomicfu")
}

android {
    namespace = "ru.vs.rsub.client"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)

                implementation(coreLibs.kotlin.atomicfu)
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
