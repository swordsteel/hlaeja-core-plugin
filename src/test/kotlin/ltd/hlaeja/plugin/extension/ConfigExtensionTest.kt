package ltd.hlaeja.plugin.extension

import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * This test uses TEST_ENV set in gradle test task, and project extension to pretend to be a project property.
 */
class ConfigExtensionTest {

    companion object {
        const val EXTENSION = "config"
        const val PLUGIN_ID = "ltd.hlaeja.plugin.hlaeja-core-plugin"
        const val PROJECT_NAME = "test-project"
        const val SNAPSHOT_VERSION = "0.0.0-SNAPSHOT"
    }

    lateinit var project: Project

    lateinit var configExtension: ConfigExtension

    @BeforeEach
    fun beforeEach() {
        project = ProjectBuilder.builder()
            .withName(PROJECT_NAME)
            .build()
        project.version = SNAPSHOT_VERSION
        project.pluginManager.apply(PLUGIN_ID)
        project.extensions.add(
            "testProperty",
            object {
                override fun toString(): String = "hlæja"
            },
        )
        configExtension = project.extensions.getByName(EXTENSION) as ConfigExtension
    }

    @Nested
    inner class FindTest {

        @Test
        fun `property or environment find property`() {
            // when
            val result = configExtension.find("testProperty", "TEST_PROPERTY")

            // then
            assertThat(result).isEqualTo("hlæja")
        }

        @Test
        fun `property or environment find environment`() {
            // when
            val result = configExtension.find("test_env", "TEST_ENV")

            // then
            assertThat(result).isEqualTo("hlæja")
        }

        @Test
        fun `property or environment find nothing`() {
            // when
            val result = configExtension.find("test", "TEST")

            // then
            assertThat(result).isNull()
        }
    }

    @Nested
    inner class FindOrDefaultTest {

        @Test
        fun `property, environment, or default find property`() {
            // when
            val result = configExtension.findOrDefault("testProperty", "TEST_PROPERTY")

            // then
            assertThat(result).isEqualTo("hlæja")
        }

        @Test
        fun `property, environment, or default find environment`() {
            // when
            val result = configExtension.findOrDefault("test_env", "TEST_ENV")

            // then
            assertThat(result).isEqualTo("hlæja")
        }

        @Test
        fun `property, environment, or default find empty`() {
            // when
            val result = configExtension.findOrDefault("test", "TEST")

            // then
            assertThat(result).isEmpty()
        }

        @Test
        fun `property, environment, or default find defined response`() {
            // when
            val result = configExtension.findOrDefault("test", "TEST", "value")

            // then
            assertThat(result).isEqualTo("value")
        }
    }
}
