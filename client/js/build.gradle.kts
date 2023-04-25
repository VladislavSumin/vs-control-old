import ru.vs.build_logic.utils.configureJsDistribution

plugins {
    id("ru.vs.convention.kmp.js")
    id("org.jetbrains.compose")
}

compose.experimental {
    this.web.application {
    }
}

kotlin {
    js(IR) {
        binaries.executable()
    }

    sourceSets {
        named("jsMain") {
            dependencies {
                implementation(projects.client.common)
            }
        }
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
