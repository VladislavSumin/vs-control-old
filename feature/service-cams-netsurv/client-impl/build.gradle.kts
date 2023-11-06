plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.preset.entities-factory-generator")
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

                implementation(projects.feature.serversConnection.clientApi)
                implementation(projects.feature.entities.clientApi)
                implementation(coreLibs.vs.core.uikit.videoPlayer)
            }
        }
    }
}
