package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.stringProperty

abstract class ProjectConfiguration constructor(private val project: Project) {
    val version = project.stringProperty("ru.vs.control.version")
}

val Project.configuration: ProjectConfiguration
    get() = extensions.findByType() ?: extensions.create(ProjectConfiguration::class.java.simpleName)
