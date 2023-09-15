plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.resources")
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

// TODO подождать пока mokko-resources обновится
//multiplatformResources {
//    multiplatformResourcesPackage = "ru.vs.control.servers.client_impl"
//}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.servers.clientApi)
                implementation(projects.feature.aboutServer.clientApi)
                implementation(projects.feature.serversConnection.clientApi)
                implementation(coreLibs.sqldelight.coroutines)
            }
        }
    }
}
