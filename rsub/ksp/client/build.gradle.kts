plugins {
    kotlin("jvm")
}

dependencies {
    implementation(projects.rsub.client)

    implementation(coreLibs.vs.core.coroutines)
    implementation(coreLibs.kotlin.serialization.core)
    implementation(coreLibs.kotlin.serialization.json)
    implementation(coreLibs.ksp)
    implementation(coreLibs.kotlinpoet.core)
    implementation(coreLibs.kotlinpoet.ksp)
}
