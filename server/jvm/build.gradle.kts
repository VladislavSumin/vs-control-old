import ru.vs.build_logic.utils.fatJar

plugins {
    id("ru.vs.convention.kmp.jvm")
}

val mainClass: String = "ru.vs.control.MainKt"

kotlin {
    jvm {
        fatJar(mainClass, jarName = "control")
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.server.common)
            }
        }
    }
}
