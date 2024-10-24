package ltd.hlaeja.plugin

import ltd.hlaeja.plugin.extension.InfoExtension
import ltd.hlaeja.plugin.extension.GitExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import ltd.hlaeja.plugin.extension.InfoExtension.Companion.PLUGIN_NAME as INFO_PLUGIN_NAME
import ltd.hlaeja.plugin.extension.GitExtension.Companion.PLUGIN_NAME as GIT_PLUGIN_NAME

@Suppress("unused")
class CorePlugin : Plugin<Project> {

    /**
     * Register Extensions and Tasks.
     */
    override fun apply(project: Project) {
        gitExtension(project)
        infoExtension(project)
    }

    private fun infoExtension(
        project: Project,
    ): InfoExtension = project.extensions.create(INFO_PLUGIN_NAME, InfoExtension::class.java, project)

    private fun gitExtension(
        project: Project,
    ): GitExtension = project.extensions.create(GIT_PLUGIN_NAME, GitExtension::class.java, project)
}
