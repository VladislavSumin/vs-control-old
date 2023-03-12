tasks.register("ci") {
    dependsOn(":client:android:assembleDebug")
    dependsOn(":client:android:assembleRelease")
    dependsOn(":client:jvm:buildFatJar")
}
