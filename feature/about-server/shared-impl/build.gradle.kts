import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.preset.feature-shared-impl")
}

android {
    namespace = "ru.vs.control.about_server.shared"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
    }
}
