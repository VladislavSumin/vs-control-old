plugins {
    id("ru.vs.convention.kmp.js")
}

kotlin {
    js(IR) {
        binaries.executable()
    }
}

// Configure configurations for delivery compiled js client to server as static resources

val browserDevDist: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

val browserProdDist: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(
        browserDevDist.name,
        tasks.named("jsBrowserDevelopmentExecutableDistribution").map { it.outputs.files.files.single() }
    )

    add(
        browserProdDist.name,
        tasks.named("jsBrowserDistribution").map { it.outputs.files.files.single() }
    )
}