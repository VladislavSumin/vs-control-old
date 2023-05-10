plugins {
    id("ru.vs.convention.preset.feature-shared-api")
}

android {
    namespace = "ru.vs.control.services.shared_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.core.id)
            }
        }
    }
}
