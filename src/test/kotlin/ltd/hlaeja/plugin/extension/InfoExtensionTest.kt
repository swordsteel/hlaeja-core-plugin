package ltd.hlaeja.plugin.extension

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.OffsetDateTime
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InfoExtensionTest {

    companion object {
        const val BASIC_TIMESTAMP = "2002-02-20T02:10:11+01:00"
        const val EXTENSION = "info"
        const val PLUGIN_ID = "ltd.hlaeja.plugin.hlaeja-core-plugin"
        const val PROJECT_NAME = "test-project"
        const val SNAPSHOT_VERSION = "0.0.0-SNAPSHOT"

        @JvmStatic
        @BeforeAll
        fun buildUp() {
            mockkStatic(OffsetDateTime::class)
            every { OffsetDateTime.now() } returns OffsetDateTime.parse(BASIC_TIMESTAMP)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            unmockkStatic(OffsetDateTime::class)
        }
    }

    lateinit var project: Project

    @BeforeEach
    fun beforeEach() {
        project = ProjectBuilder.builder().withName(PROJECT_NAME).build()
        project.version = SNAPSHOT_VERSION
        project.pluginManager.apply(PLUGIN_ID)
    }

    @Test
    fun `get vendor`() {
        // when
        val extension = project.extensions.getByName(EXTENSION) as InfoExtension

        // then
        assertEquals("Hlæja Ltd", extension.vendorName)
    }

    @Test
    fun `get timestamp`() {
        // when
        val extension = project.extensions.getByName(EXTENSION) as InfoExtension

        // then
        assertEquals("2002-02-20 01:10:11 UTC", extension.utcTimestamp)
    }

    @Test
    fun `get build`() {
        // when
        val extension = project.extensions.getByName(EXTENSION) as InfoExtension

        // then
        assertEquals("Project Name: test-project Version: 0.0.0-SNAPSHOT", extension.nameVersion)
    }
}
