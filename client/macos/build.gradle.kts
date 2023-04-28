plugins {
    id("ru.vs.convention.kmp.macos")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
}

kotlin {
    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                )
            }
        }
    }

    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
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
