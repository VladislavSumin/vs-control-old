package ru.vs.build_logic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import ru.vs.build_logic.utils.Configuration
import ru.vs.build_logic.utils.PropertyProvider

/**
 * Project configuration class
 * proxies all external configuration
 */
@Suppress("UnnecessaryAbstractClass")
abstract class ProjectConfiguration(
    propertyProvider: PropertyProvider
) : Configuration("ru.vs.control", propertyProvider) {
    val version: String = property("version")
    val sentry = Sentry()

    inner class Sentry : Configuration("sentry", this) {
        val serverToken: String = property("serverToken")
    }
}

val Project.configuration: ProjectConfiguration
    get() = extensions.findByType()
        ?: extensions.create(
            ProjectConfiguration::class.java.simpleName,
            PropertyProvider { project.findProperty(it)?.toString() }
        )
