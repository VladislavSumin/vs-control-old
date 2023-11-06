plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.preset.entities-factory-generator")
}

android {
    namespace = "ru.vs.control.entities.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.entities.clientApi)
                api(projects.feature.entities.sharedImpl)

                implementation(projects.feature.serversConnection.clientApi)
            }
        }
    }
}
