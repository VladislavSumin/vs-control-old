plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":rsub:core"))
    api(libs.ktor.cio)
}
