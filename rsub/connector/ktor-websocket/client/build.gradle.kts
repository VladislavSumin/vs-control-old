plugins {
    kotlin("jvm")
}

dependencies {
    api(projects.rsub.connector.ktorWebsocket.core)

    implementation(projects.rsub.client)
    implementation(coreLibs.ktor.client.websocket)
}
