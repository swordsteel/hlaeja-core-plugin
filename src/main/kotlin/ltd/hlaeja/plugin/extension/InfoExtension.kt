package ltd.hlaeja.plugin.extension

import java.time.OffsetDateTime.now
import java.time.ZoneId.of
import java.time.format.DateTimeFormatter.ofPattern
import org.gradle.api.Project

abstract class InfoExtension(private val project: Project) {
    companion object {
        const val PLUGIN_NAME = "info"
    }

    val nameVersion get() = "Project Name: ${project.name} Version: ${project.version}"
    val utcTimestamp = now().atZoneSameInstant(of("UTC")).format(ofPattern("yyyy-MM-dd HH:mm:ss z")).toString()
    val vendorName = "Hlæja Ltd"
}
