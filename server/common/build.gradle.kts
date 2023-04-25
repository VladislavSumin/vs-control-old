import ru.vs.build_logic.configuration

plugins {
    id("ru.vs.convention.preset.server-feature")
    id("com.github.gmazzo.buildconfig")
    id("com.google.devtools.ksp")
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
                implementation(project(mapOf("path" to ":client:js", "configuration" to "browserProdDist")))
            }
        }
    }
}

dependencies {
    // TODO завести конвеншен под это дело
//    add("kspCommonMainMetadata", projects.rsub.ksp.server)
//    add("kspJvm", projects.rsub.ksp.server)
}