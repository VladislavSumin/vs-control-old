import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

android {
    namespace = "ru.vs.control.about_server.client_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.aboutServer.sharedImpl)
                api(projects.feature.servers.clientApi)
            }
        }
    }
}
