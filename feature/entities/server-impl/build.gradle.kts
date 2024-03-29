plugins {
    id("ru.vs.convention.preset.feature-server-impl")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.entities.serverApi)
                api(projects.feature.entities.sharedImpl)
            }
        }
    }
}
