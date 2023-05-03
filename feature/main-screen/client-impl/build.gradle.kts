plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.resources")
}

android {
    namespace = "ru.vs.control.main_screen.client_impl"
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.vs.control.main_screen.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.feature.rootNavigation.clientApi)

                implementation(projects.feature.entities.clientImpl)
                implementation(projects.feature.servers.clientImpl)
            }
        }
    }
}
