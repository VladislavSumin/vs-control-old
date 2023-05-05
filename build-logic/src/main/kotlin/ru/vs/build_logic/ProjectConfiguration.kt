package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.stringProperty

/**
 * Project configuration class
 * proxies all external configuration (by properties or by environment variables
 */
@Suppress("UnnecessaryAbstractClass")
abstract class ProjectConfiguration(private val project: Project) {
    val version = project.stringProperty("ru.vs.control.version")
}

val Project.configuration: ProjectConfiguration
    get() = extensions.findByType() ?: extensions.create(ProjectConfiguration::class.java.simpleName)
