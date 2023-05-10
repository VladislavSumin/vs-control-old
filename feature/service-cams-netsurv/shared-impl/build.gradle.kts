plugins {
    id("ru.vs.convention.preset.feature-shared-impl")
}

android {
    namespace = "ru.vs.control.service_cams_netsurv.shared_impl"
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
