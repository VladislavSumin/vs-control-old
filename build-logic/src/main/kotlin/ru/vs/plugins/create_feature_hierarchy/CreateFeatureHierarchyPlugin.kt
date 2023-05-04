package ru.vs.plugins.create_feature_hierarchy

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class CreateFeatureHierarchyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        check(target == target.rootProject) {
            "CreateFeatureHierarchyPlugin can be applied only to rootProject, you trying to apply it to ${target.name}"
        }

        target.tasks.register<CreateFeatureHierarchyTask>(CREATE_FEATURE_HIERARCHY_TASK_NAME)
    }

    companion object {
        private const val CREATE_FEATURE_HIERARCHY_TASK_NAME = "createFeatureHierarchy"
    }
}
