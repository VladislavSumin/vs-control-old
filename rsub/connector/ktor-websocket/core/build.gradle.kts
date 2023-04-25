plugins {
    kotlin("jvm")
}

dependencies {
    api(projects.rsub.core)
    implementation(coreLibs.ktor.websockets)
}
