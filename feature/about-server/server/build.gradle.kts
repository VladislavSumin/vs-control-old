plugins {
    id("ru.vs.convention.preset.server-feature")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.aboutServer.shared)
            }
        }
    }
}
