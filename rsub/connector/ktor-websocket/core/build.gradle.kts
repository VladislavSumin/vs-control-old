plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)
                implementation(coreLibs.ktor.websockets)
            }
        }
    }
}
