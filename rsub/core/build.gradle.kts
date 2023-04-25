plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(coreLibs.vs.core.coroutines)
    implementation(coreLibs.kotlin.serialization.core)
    implementation(coreLibs.kotlin.serialization.json)
}
