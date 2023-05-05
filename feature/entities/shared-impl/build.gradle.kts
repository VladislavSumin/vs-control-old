plugins {
    id("ru.vs.convention.preset.feature-shared-impl")
}

android {
    namespace = "ru.vs.control.entities.shared_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.entities.sharedApi)
                implementation(projects.feature.entities.dto)
            }
        }
    }
}
