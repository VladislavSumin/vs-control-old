import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        register("Database") {
            packageName.set("ru.vs.control.servers.repository")
        }
    }
}

android {
    namespace = "ru.vs.control.servers.client_impl"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.servers.clientApi)
                implementation(coreLibs.sqldelight.coroutines)
            }
        }
    }
}
