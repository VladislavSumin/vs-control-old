plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)

                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
