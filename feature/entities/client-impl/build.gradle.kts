plugins {
    id("ru.vs.convention.preset.feature-client-impl")
}
android {
    namespace = "ru.vs.control.entities.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
    }
}
