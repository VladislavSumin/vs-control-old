plugins {
    id("ru.vs.convention.preset.feature-client-impl")
}

android {
    namespace = "ru.vs.control.service_cams_netsurv.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.serviceCamsNetsurv.clientApi)
                api(projects.feature.serviceCamsNetsurv.sharedImpl)

                implementation(projects.feature.entities.clientApi)
            }
        }
    }
}
