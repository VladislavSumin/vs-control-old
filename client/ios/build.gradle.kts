import org.jetbrains.compose.experimental.dsl.IOSDevices

plugins {
    id("ru.vs.convention.kmp.ios")
    id("org.jetbrains.compose")
}

compose.experimental {
    uikit.application {
        bundleIdPrefix = "ru.vs"
        projectName = "Control"
        deployConfigurations {
            simulator("IPhone13") {

                // Usage: ./gradlew iosDeployIPhone13Debug
                device = IOSDevices.IPHONE_13_PRO
            }
            simulator("IPadUI") {
                // Usage: ./gradlew iosDeployIPadUIDebug
                device = IOSDevices.IPAD_MINI_6th_Gen
            }
        }
    }
}

kotlin {

    iosX64("uikitX64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }
    iosArm64("uikitArm64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
                // TODO: the current compose binary surprises LLVM, so disable checks for now.
                freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
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
