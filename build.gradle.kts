plugins {
    id("ru.vs.convention.check-updates")
}

tasks.register("ci") {
    // Client
    dependsOn(":client:android:assembleDebug")
    dependsOn(":client:android:assembleRelease")
    dependsOn(":client:jvm:buildFatJarMain")
    dependsOn(":client:common:iosArm64MainBinaries")

    // Server
    dependsOn(":server:jvm:buildFatJarMain")
}
