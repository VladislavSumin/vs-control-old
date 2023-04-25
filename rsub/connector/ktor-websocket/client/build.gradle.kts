plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":rsub:client"))
    api(project(":rsub:connector:ktor-websocket:core"))
    api(libs.ktor.client.websocket)
}
