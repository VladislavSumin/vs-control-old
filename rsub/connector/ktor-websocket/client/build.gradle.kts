plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.connector.ktorWebsocket.core)

                implementation(projects.rsub.client)
                implementation(coreLibs.ktor.client.websocket)
            }
        }
    }
}
