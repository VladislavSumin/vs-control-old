plugins {
    id("ru.vs.convention.preset.feature-server-api")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceCamsNetsurv.sharedApi)
            }
        }
    }
}
