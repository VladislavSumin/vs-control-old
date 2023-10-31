import ru.vs.build_logic.utils.fatJar

plugins {
    id("ru.vs.convention.kmp.jvm")
}

val mainClass: String = "ru.vs.control.MainKt"

kotlin {
    jvm {
        fatJar(
            mainClass = mainClass,
            jarName = "control",
            duplicatesStrategy = DuplicatesStrategy.WARN
        )
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                api(projects.server.common)
            }
        }
    }
}
