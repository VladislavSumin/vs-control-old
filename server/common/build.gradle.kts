import ru.vs.build_logic.configuration

plugins {
    id("ru.vs.convention.preset.feature-server-impl")
    id("ru.vs.convention.serialization.json")
    id("ru.vs.convention.ksp-kmp-hack")
    id("com.github.gmazzo.buildconfig")
}

buildConfig {
    className("BuildConfig")
    packageName("ru.vs.control")
    buildConfigField("String", "version", "\"${project.configuration.version}\"")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                // Add client js compiled code as a static resource
                // implementation(project(mapOf("path" to ":client:js", "configuration" to "browserProdDist")))

                implementation(projects.feature.aboutServer.server)
                implementation(projects.feature.entities.serverImpl)
                implementation(projects.feature.serviceCamsNetsurv.serverImpl)
                implementation(projects.feature.services.serverImpl)

                implementation(coreLibs.vs.core.ktor.server)
                implementation(coreLibs.ktor.server.websocket)

                implementation(projects.rsub.server)
                implementation(projects.rsub.connector.ktorWebsocket.server)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.rsub.ksp.server)
}
