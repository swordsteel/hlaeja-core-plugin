package ltd.hlaeja.plugin.extension

import java.lang.System.getenv
import org.gradle.api.Project

abstract class ConfigExtension(private val project: Project) {

    companion object {
        const val PLUGIN_NAME = "config"
        const val EMPTY = ""
    }

    fun find(
        property: String,
        environment: String,
    ): String? = findProperty(property) ?: findEnvironment(environment)

    fun findOrDefault(
        property: String,
        environment: String,
        default: String = EMPTY,
    ): String = findProperty(property) ?: findEnvironment(environment) ?: default

    private fun findProperty(
        property: String,
    ) = project.findProperty(property)?.toString()

    private fun findEnvironment(
        environment: String,
    ): String? = getenv(environment)
}
