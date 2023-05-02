plugins {
    id("ru.vs.convention.preset.feature-client-impl")
}
android {
    namespace = "ru.vs.control.main_screen.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.feature.rootNavigation.clientApi)
                implementation(projects.feature.servers.clientImpl)
            }
        }
    }
}
