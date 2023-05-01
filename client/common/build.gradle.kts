import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.kmp.cocoapods")
    id("ru.vs.convention.resources")
    id("app.cash.sqldelight")
}

evaluationDependsOn(":feature:servers:client-impl")
sqldelight {
    databases {
        register("Database") {
            packageName.set("ru.vs.control.repository")
            dependency(projects.feature.servers.clientImpl)
        }
    }
}

android {
    namespace = "ru.vs.control.common"
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.vs.control.common"
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
                implementation(projects.feature.servers.clientImpl)
                implementation(projects.feature.serversConnection.clientImpl)

                implementation(coreLibs.vs.core.database)
            }
        }
    }
}
