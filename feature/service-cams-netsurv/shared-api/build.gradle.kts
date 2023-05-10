plugins {
    id("ru.vs.convention.preset.feature-shared-api")
}

android {
    namespace = "ru.vs.control.service_cams_netsurv.shared_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
    }
}
