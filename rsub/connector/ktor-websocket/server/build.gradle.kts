plugins {
    kotlin("jvm")
}

dependencies {
    api(projects.rsub.connector.ktorWebsocket.core)

    implementation(projects.rsub.server)
    implementation(coreLibs.ktor.server.core)
    implementation(coreLibs.ktor.server.websocket)
}
