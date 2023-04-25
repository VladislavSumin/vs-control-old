plugins {
    kotlin("jvm")
}

dependencies {
    api(projects.rsub.core)

    implementation(coreLibs.vs.core.coroutines)
    implementation(coreLibs.vs.core.logging)
    implementation(coreLibs.kotlin.serialization.core)
    implementation(coreLibs.kotlin.serialization.json)
}
