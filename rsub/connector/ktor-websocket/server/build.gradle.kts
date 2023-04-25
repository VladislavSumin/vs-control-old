plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":rsub:server"))
    api(project(":rsub:connector:ktor-websocket:core"))
    api(libs.ktor.server.websocket)
}
