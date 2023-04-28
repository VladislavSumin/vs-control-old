plugins {
    id("ru.vs.convention.kmp.macos")
    id("org.jetbrains.compose")
}

kotlin {
    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework",
                    "-linker-option", "Metal",

                    // for sqldelight
                    "-linker-options", "-lsqlite3"
                )
            }
        }
    }

    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework",
                    "-linker-option", "Metal",

                    // for sqldelight
                    "-linker-options", "-lsqlite3"
                )
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
