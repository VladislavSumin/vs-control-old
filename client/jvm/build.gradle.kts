import ru.vs.build_logic.utils.fatJar

plugins {
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.compose")
}

val clientMainClass: String = "ru.vs.control.MainKt"

kotlin {
    jvm {
        fatJar(
            mainClass = clientMainClass,
            jarName = "control",

            // TODO Moko-resources add duplicating resources
            // preview-desktop, and some other libs (not is moko generated resources)
            duplicatesStrategy = DuplicatesStrategy.WARN,
        )
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.client.common)

                implementation(coreLibs.vs.core.compose)
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.decompose)
                implementation(coreLibs.vs.core.di)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
