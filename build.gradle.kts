import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("ru.vs.convention.check-updates")
    id("ru.vs.convention.analyze.check-module-graph")
    id("ru.vs.plugins.create-feature-hierarchy")
}

// Setup detekt for all projects
allprojects {
    apply { plugin("ru.vs.convention.analyze.detekt") }
}

// Add additional configuration to check build-logic
val detektBuildLogic = tasks.register<Detekt>("detektBuildLogic") {
    source = fileTree(project.rootDir).matching {
        include("build-logic/src/**/*.kt", "build-logic/**/*.gradle.kts")
        exclude("**/build/**")
    }
}
tasks.named("detekt").configure { dependsOn(detektBuildLogic) }

// Add custom allowance for assertions plugin
moduleGraphAssert {
    allowed += arrayOf(
        ":client:.* -> .*",
        ":server:.* -> .*",
        ".* -> :rsub:.*",
        ".*client-impl -> .*shared-impl",
        ".*server-impl -> .*shared-impl",
    )
}

tasks.register("ci") {
    // Client
    dependsOn(":client:android:assembleDebug")
    // dependsOn(":client:android:assembleRelease")
    dependsOn(":client:jvm:buildFatJarMain")
    dependsOn(":client:js:jsBrowserDistribution")
    // TODO internal ktor error see https://youtrack.jetbrains.com/issue/KTOR-5728
    // dependsOn(":client:macos:macosArm64Binaries")
    // dependsOn(":client:macos:macosX64Binaries")
    // dependsOn(":client:ios:uikitArm64Binaries")
    // dependsOn(":client:ios:uikitX64Binaries")

    // Server
    dependsOn(":server:jvm:buildFatJarMain")

    // Tests
    allprojects {
        val allTests = tasks.findByName("allTest")
        if (allTests != null) dependsOn(allTests)
    }

    // Check dependencies
    dependsOn(":dependencyUpdates")
    dependsOn(gradle.includedBuild("vs-core-kt").task(":dependencyUpdates"))
}
