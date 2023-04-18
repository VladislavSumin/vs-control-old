import ru.vs.build_logic.configuration

plugins {
    id("ru.vs.convention.preset.server-feature")
    id("com.github.gmazzo.buildconfig")
}

buildConfig {
    className("BuildConfig")
    packageName("ru.vs.control")
    buildConfigField("String", "version", "\"${project.configuration.version}\"")
}
