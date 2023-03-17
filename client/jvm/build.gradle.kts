import ru.vs.build_logic.utils.fatJar

plugins {
    id("ru.vs.convention.kmp.jvm")
}

val clientMainClass: String = "ru.vs.control.MainKt"

kotlin {
    jvm {
        fatJar(clientMainClass, jarName = "control")
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.client.common)
            }
        }
    }
}
