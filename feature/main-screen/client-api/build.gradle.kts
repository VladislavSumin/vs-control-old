plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

android {
    namespace = "ru.vs.control.main_screen.client_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.rootNavigation.clientApi)
            }
        }
    }
}
