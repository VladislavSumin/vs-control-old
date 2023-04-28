import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.client-feature")
    id("ru.vs.convention.kmp.cocoapods")
    id("app.cash.sqldelight")
}

evaluationDependsOn(":feature:servers:client")
sqldelight {
    databases {
        register("Database") {
            packageName.set("ru.vs.control.repository")
            dependency(projects.feature.servers.client)
        }
    }
}

android {
    namespace = "ru.vs.control.common"
}

kotlin {
    cocoapods {
        version = "0.0.1"
        summary = "control-common library"
        name = "control-common"
        homepage = "https://github.com/VladislavSumin/vs-control"
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.feature.servers.client)
                implementation(coreLibs.vs.core.database)
            }
        }
    }
}
