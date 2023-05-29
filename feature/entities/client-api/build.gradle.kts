plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

android {
    namespace = "ru.vs.control.entities.client_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.entities.sharedApi)
                implementation(projects.feature.servers.clientApi)
            }
        }
    }
}
