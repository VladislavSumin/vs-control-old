import ru.vs.build_logic.utils.configureJsDistribution

plugins {
    id("ru.vs.convention.kmp.js")
}

kotlin {
    js(IR) {
        binaries.executable()
    }
}

// Configure configurations for delivery compiled js client to server as static resources
configureJsDistribution(
    configurationName = "browserProdDist",
    jsBuildTask = "jsBrowserDistribution"
)

configureJsDistribution(
    configurationName = "browserDevDist",
    jsBuildTask = "jsBrowserDevelopmentExecutableDistribution"
)
