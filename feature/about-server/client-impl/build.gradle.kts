plugins {
    id("ru.vs.convention.preset.feature-client-impl")
}
android {
    namespace = "ru.vs.control.about_server.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.aboutServer.clientApi)

                implementation(projects.feature.aboutServer.sharedImpl)
                implementation(projects.feature.serversConnection.clientApi)
            }
        }
    }
}
