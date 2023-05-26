plugins {
    id("ru.vs.convention.preset.feature-server-impl")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceDebug.serverApi)
                api(projects.feature.serviceDebug.sharedImpl)

                implementation(projects.feature.entities.serverApi)
                implementation(projects.feature.services.serverApi)
            }
        }
    }
}
