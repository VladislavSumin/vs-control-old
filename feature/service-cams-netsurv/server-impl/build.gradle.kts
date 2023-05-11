plugins {
    id("ru.vs.convention.preset.feature-server-impl")
    id("ru.vs.convention.serialization.json")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceCamsNetsurv.serverApi)
                api(projects.feature.serviceCamsNetsurv.sharedImpl)

                implementation(projects.feature.entities.serverApi)
                implementation(projects.feature.services.serverApi)
                implementation(coreLibs.vs.core.ktor.network)
            }
        }
    }
}
