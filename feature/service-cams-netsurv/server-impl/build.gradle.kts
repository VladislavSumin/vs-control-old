plugins {
    id("ru.vs.convention.preset.feature-server-impl")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceCamsNetsurv.serverApi)
                api(projects.feature.serviceCamsNetsurv.sharedImpl)

                api(projects.feature.services.serverApi)
            }
        }
    }
}
