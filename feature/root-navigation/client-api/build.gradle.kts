plugins {
    id("ru.vs.convention.preset.feature-client-api")
}
android {
    namespace = "ru.vs.control.root_navigation.client_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
    }
}
