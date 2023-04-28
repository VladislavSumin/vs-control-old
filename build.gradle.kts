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
    dependsOn(":client:js:jsBrowserDistribution")
    dependsOn(":client:macos:macosArm64Binaries")
    dependsOn(":client:macos:macosX64Binaries")
    dependsOn(":client:ios:uikitArm64Binaries")
    dependsOn(":client:ios:uikitX64Binaries")

    // Server
    dependsOn(":server:jvm:buildFatJarMain")

    // Check dependencies
    dependsOn(":dependencyUpdates")
    dependsOn(gradle.includedBuild("vs-core-kt").task(":dependencyUpdates"))
}
