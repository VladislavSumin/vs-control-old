tasks.register("ci") {
    dependsOn(":client:android:assembleDebug")
    dependsOn(":client:android:assembleRelease")
    dependsOn(":client:jvm:buildFatJar")

    dependsOn(":client:common:iosArm64MainBinaries")
}
