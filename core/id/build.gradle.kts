plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.control.core.id"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
    }
}
