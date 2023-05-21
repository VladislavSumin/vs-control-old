import ru.vs.build_logic.utils.android

plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

android {
    namespace = "ru.vs.control.servers_connection.client_api"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.feature.servers.clientApi)

                // TODO проверка гипотизы, подключаем только как имплементацию
                implementation(projects.feature.aboutServer.sharedImpl)
                implementation(projects.feature.entities.sharedImpl)
            }
        }
    }
}
