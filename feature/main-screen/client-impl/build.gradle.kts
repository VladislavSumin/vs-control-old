plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.resources")
}

android {
    namespace = "ru.vs.control.main_screen.client_impl"
}

// TODO подождать пока mokko-resources обновится
// multiplatformResources {
//    multiplatformResourcesPackage = "ru.vs.control.main_screen.client_impl"
// }

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.feature.mainScreen.clientApi)

                implementation(projects.feature.entities.clientApi)
                implementation(projects.feature.servers.clientApi)
                implementation(projects.feature.services.clientApi)
            }
        }
    }
}
