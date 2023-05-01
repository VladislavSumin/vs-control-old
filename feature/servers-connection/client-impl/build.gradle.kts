import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.ksp-kmp-hack")
}

android {
    namespace = "ru.vs.control.servers_connection.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.feature.servers.clientApi)

                implementation(projects.rsub.client)
                implementation(projects.rsub.connector.ktorWebsocket.client)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.rsub.ksp.client)
}
