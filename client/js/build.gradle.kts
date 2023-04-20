plugins {
    id("ru.vs.convention.kmp.js")
}

kotlin {
    js(IR) {
        binaries.executable()
    }
}
