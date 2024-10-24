package ltd.hlaeja.plugin

import ltd.hlaeja.plugin.extension.GitExtension
import ltd.hlaeja.plugin.extension.GitExtension.Companion.PLUGIN_NAME as GIT_PLUGIN_NAME
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class CorePlugin : Plugin<Project> {

    /**
     * Register Extensions and Tasks.
     */
    override fun apply(project: Project) {
        gitExtension(project)
    }

    private fun gitExtension(
        project: Project,
    ): GitExtension = project.extensions.create(GIT_PLUGIN_NAME, GitExtension::class.java, project)
}
