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

                // TODO Студия не корректно определяет classpath при запуске через стандартную конфигурацию
                // как workaround пока нужно тут прописывать явно все зависимости
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.di)
                implementation(coreLibs.vs.core.ktor.server)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
