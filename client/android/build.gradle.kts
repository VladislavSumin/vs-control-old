plugins {
    id("ru.vs.convention.android.application")
}

android {
    namespace = "ru.vs.control"

    defaultConfig {
        applicationId = "ru.vs.control"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"

            // TODO похоже на баг в корутинах, попробовать убрать после обновления версии
            excludes += "/META-INF/**/previous-compilation-data.bin"
        }
    }
}

dependencies {
    implementation(projects.client.common)

    implementation(coreLibs.vs.core.compose)
    implementation(coreLibs.vs.core.coroutines)
    implementation(coreLibs.vs.core.di)
    implementation(coreLibs.vs.core.logging)

    implementation(coreLibs.android.core)
    implementation(coreLibs.android.activity)

}
