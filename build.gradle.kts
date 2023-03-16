tasks.register("ci") {
    // Client
    dependsOn(":client:android:assembleDebug")
    dependsOn(":client:android:assembleRelease")
    dependsOn(":client:jvm:buildFatJar")
    dependsOn(":client:common:iosArm64MainBinaries")

    // Server
    dependsOn(":server:jvm:buildFatJar")
}
