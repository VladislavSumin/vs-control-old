plugins {
    id("ru.vs.convention.preset.feature-client-impl")
}

android {
    namespace = "ru.vs.control.service_debug.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceDebug.clientApi)
                api(projects.feature.serviceDebug.sharedImpl)
            }
        }
    }
}
