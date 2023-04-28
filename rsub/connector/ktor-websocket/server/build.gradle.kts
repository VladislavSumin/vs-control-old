plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.connector.ktorWebsocket.core)

                implementation(projects.rsub.server)
                implementation(coreLibs.ktor.server.core)
                implementation(coreLibs.ktor.server.websocket)
            }
        }
    }
}
