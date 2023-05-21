plugins {
    id("ru.vs.convention.preset.feature-client-impl")
}
android {
    namespace = "ru.vs.control.root_navigation.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.rootNavigation.clientApi)

                implementation(projects.feature.mainScreen.clientImpl)
                implementation(projects.feature.servers.clientApi)
            }
        }
    }
}
