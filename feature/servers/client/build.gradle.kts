import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.client-feature")
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
    namespace = "ru.vs.control.servers.client"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.sqldelight.coroutines)
            }
        }
    }
}
