plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.preset.entities-factory-generator")
}

android {
    namespace = "ru.vs.control.services.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.services.clientApi)
                api(projects.feature.services.sharedImpl)

                implementation(projects.feature.entities.clientApi)
            }
        }
    }
}
