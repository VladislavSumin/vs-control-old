plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies {
    // TODO добавить корректную версию
    implementation("ru.vs.core:core-build-logic:0.1.0")
}

gradlePlugin {
    plugins {
        create("createFeatureHierarchyPlugin") {
            id = "ru.vs.plugins.create-feature-hierarchy"
            implementationClass = "ru.vs.plugins.create_feature_hierarchy.CreateFeatureHierarchyPlugin"
        }
    }
}
