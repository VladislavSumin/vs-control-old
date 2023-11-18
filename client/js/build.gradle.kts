import ru.vs.build_logic.utils.configureJsDistribution

plugins {
    id("ru.vs.convention.kmp.js")
    id("ru.vs.convention.compose")
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
                implementation(npm("sql.js", "1.6.2"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
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
