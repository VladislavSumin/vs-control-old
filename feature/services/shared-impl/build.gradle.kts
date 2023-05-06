plugins {
    id("ru.vs.convention.preset.feature-shared-impl")
}

android {
    namespace = "ru.vs.control.services.shared_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.services.sharedApi)
            }
        }
    }
}
