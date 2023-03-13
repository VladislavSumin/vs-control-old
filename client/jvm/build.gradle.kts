plugins {
    id("ru.vs.convention.kmp.jvm")
}

val clientMainClass: String = "ru.vs.control.MainKt"

kotlin {
    jvm {
        compilations {
            val main = getByName("main")
            tasks {
                // See https://stackoverflow.com/questions/57168853/create-fat-jar-from-kotlin-multiplatform-project
                register<Jar>("buildFatJar") {
                    group = "application"
                    manifest {
                        attributes["Main-Class"] = clientMainClass
                    }
                    archiveBaseName.set("control")

                    val dependencies = main.compileDependencyFiles.map { zipTree(it) }
                    from(main.output.classesDirs, dependencies)

                    exclude("META-INF/versions/**")
                    exclude("META-INF/*.kotlin_module")
                }
            }
        }

    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.client.common)
            }
        }
    }
}
