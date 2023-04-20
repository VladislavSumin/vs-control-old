plugins {
    id("ru.vs.convention.check-updates")
}

allprojects {
    apply { plugin("ru.vs.convention.analyze.detekt") }
}

tasks.register("ci") {
    // Client
    dependsOn(":client:android:assembleDebug")
    dependsOn(":client:android:assembleRelease")
    dependsOn(":client:jvm:buildFatJarMain")
    dependsOn(":client:js:jsBrowserProductionWebpack")
    dependsOn(":client:common:iosArm64MainBinaries")

    // Server
    dependsOn(":server:jvm:buildFatJarMain")

    // Check dependencies
    dependsOn(":dependencyUpdates")
    dependsOn(gradle.includedBuild("vs-core-kt").task(":dependencyUpdates"))
}
