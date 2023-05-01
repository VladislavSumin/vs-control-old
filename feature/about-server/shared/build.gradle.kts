import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
}

android {
    namespace = "ru.vs.control.about_server.shared"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.rsub.core)
            }
        }
    }
}
