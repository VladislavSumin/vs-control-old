plugins {
    id("ru.vs.convention.preset.feature-server-impl")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.services.serverApi)
                api(projects.feature.services.sharedImpl)
            }
        }
    }
}
