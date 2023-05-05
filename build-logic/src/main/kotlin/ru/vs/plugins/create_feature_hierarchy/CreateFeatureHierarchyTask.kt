package ru.vs.plugins.create_feature_hierarchy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.vs.build_logic.utils.snakeToCamelCase

/**
 * Creates default feature module structure.
 * See [FEATURE_SUBMODULES] for known witch modules are created
 * See [RESOURCE_BASE_PATH] ar resources to show default template files
 */
abstract class CreateFeatureHierarchyTask : DefaultTask() {

    init {
        // This task newer be up-to-date
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun taskAction() {
        val notExistedProjects = project
            .childProjects["feature"]!!
            .childProjects
            .map { it.value }
            .filter { !it.projectDir.exists() }

        notExistedProjects.forEach { project ->
            val projectDir = project.projectDir
            FEATURE_SUBMODULES.forEach { featureSubmoduleDescription ->
                val template = getConfigurationTemplate(featureSubmoduleDescription.configurationTemplateFileName)
                val formattedTemplate = formatConfigurationTemplate(template, project.name)
                projectDir
                    .resolve(featureSubmoduleDescription.moduleName)
                    .apply { mkdirs() }
                    .resolve("build.gradle.kts")
                    .writeText(formattedTemplate)
            }
        }
    }

    private fun formatConfigurationTemplate(
        configurationTemplate: String,
        featureName: String,
    ): String {
        val featureNameSnakeCase = featureName.replace("-", "_")
        val featureNameCamelCase = featureNameSnakeCase.snakeToCamelCase()
        return configurationTemplate
            .replace("{{ feature_name }}", featureNameSnakeCase)
            .replace("{{ featureName }}", featureNameCamelCase)
    }

    private fun getConfigurationTemplate(configurationTemplateFileName: String): String {
        return javaClass.classLoader.getResourceAsStream("$RESOURCE_BASE_PATH/$configurationTemplateFileName")!!
            .bufferedReader()
            .use { it.readText() }
    }

    private data class FeatureSubmoduleDescription(
        val moduleName: String,
        val configurationTemplateFileName: String = "$moduleName.txt"
    )

    companion object {
        private val FEATURE_SUBMODULES = setOf(
            FeatureSubmoduleDescription("shared-api"),
            FeatureSubmoduleDescription("shared-impl"),
            FeatureSubmoduleDescription("client-api"),
            FeatureSubmoduleDescription("client-impl"),
            FeatureSubmoduleDescription("server-api"),
            FeatureSubmoduleDescription("server-impl"),
        )

        private const val RESOURCE_BASE_PATH = "ru/vs/plugins/create_feature_hierarchy"
    }
}
