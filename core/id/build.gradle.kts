plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
}

android {
    namespace = "ru.vs.control.id"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
    }
}
