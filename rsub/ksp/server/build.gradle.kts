plugins {
    kotlin("jvm")
}

dependencies {
    implementation(projects.rsub.server)

    implementation(coreLibs.vs.core.coroutines)
    implementation(coreLibs.vs.core.utils)
    implementation(coreLibs.kotlin.serialization.core)
    implementation(coreLibs.kotlin.serialization.json)
    implementation(coreLibs.ksp)
    implementation(coreLibs.kotlinpoet.core)
    implementation(coreLibs.kotlinpoet.ksp)
}
