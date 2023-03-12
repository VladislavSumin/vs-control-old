plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":client:common"))
            }
        }
    }
}
