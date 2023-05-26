plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

android {
    namespace = "ru.vs.control.service_debug.client_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceDebug.sharedApi)
            }
        }
    }
}
