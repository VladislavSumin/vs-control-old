plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

sourceSets {
    main {
        java {
            srcDir("build/generated/ksp/main/kotlin")
        }
    }
}

dependencies {
    implementation(project(":rsub:connector:ktor-websocket:client"))
    implementation(project(":rsub:connector:ktor-websocket:server"))
    implementation(vsLibs.vs.core.ktor.server)
    implementation(libs.ktor.client.okhttp)
    ksp(project(":rsub:ksp:client"))
    ksp(project(":rsub:ksp:server"))
}
