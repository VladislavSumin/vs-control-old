import ru.vs.build_logic.utils.fatJar

plugins {
    id("ru.vs.convention.kmp.jvm")
    id("org.jetbrains.compose")
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

                implementation(coreLibs.vs.core.compose)
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.di)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
