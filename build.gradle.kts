import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_KOTLIN
import java.lang.System.getenv
import java.time.OffsetDateTime.now
import java.time.ZoneId.of
import java.time.format.DateTimeFormatter.ofPattern
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF

plugins {
    alias(hlaeja.plugins.io.gitlab.arturbosch.detekt)
    alias(hlaeja.plugins.kotlin.jvm)
    alias(hlaeja.plugins.org.jlleitschuh.gradle.ktlint)

    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(hlaeja.org.eclipse.jgit)

    testImplementation(hlaeja.assertj.core)
    testImplementation(hlaeja.junit.jupiter.params)
    testImplementation(hlaeja.kotlin.reflect)
    testImplementation(hlaeja.kotlin.test)
    testImplementation(hlaeja.mockk)
}

gradlePlugin.plugins.create("hlaeja-core-plugin") {
    id = "ltd.hlaeja.plugin.hlaeja-core-plugin"
    implementationClass = "ltd.hlaeja.plugin.CorePlugin"
}

description = "Hlæja Core Plugin"
group = "ltd.hlaeja.plugin"

detekt {
    buildUponDefaultConfig = true
    basePath = projectDir.path
    source.from(DEFAULT_SRC_DIR_KOTLIN, DEFAULT_TEST_SRC_DIR_KOTLIN)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withSourcesJar()
}

kotlin.compilerOptions.freeCompilerArgs.addAll("-Xjsr305=strict")

ktlint {
    verbose = true
    filter {
        include("**/kotlin/**")
    }
    reporters {
        reporter(SARIF)
    }
}

publishing {
    repositories {

        fun retrieveConfiguration(
            property: String,
            environment: String,
        ): String? = project.findProperty(property)?.toString() ?: getenv(environment)

        maven {
            url = uri("https://maven.pkg.github.com/swordsteel/${project.name}")
            name = "GitHubPackages"
            credentials {
                username = retrieveConfiguration("repository.user", "REPOSITORY_USER")
                password = retrieveConfiguration("repository.token", "REPOSITORY_TOKEN")
            }
        }
    }
    publications.create<MavenPublication>("mavenJava") { from(components["java"]) }
}

tasks {
    named("build") {
        dependsOn("buildInfo")
    }
    register("buildInfo") {
        group = "hlaeja"
        description = "Prints the project name and version"

        doLast {
            println("Project Name: ${project.name} Version: ${project.version}")
        }
    }
    withType<Detekt> {
        reports {
            html.required = false
            md.required = false
            sarif.required = true
            txt.required = false
            xml.required = false
        }
    }
    withType<Jar> {
        manifest.attributes.apply {
            put("Implementation-Title", project.name)
            put("Implementation-Version", project.version)
            put("Implementation-Vendor", "Hlæja Ltd")
            put("Built-By", System.getProperty("user.name"))
            put("Built-Gradle", project.gradle.gradleVersion)
            put("Built-JDK", System.getProperty("java.version"))
            put("Built-OS", "${System.getProperty("os.name")} v${System.getProperty("os.version")}")
            put(
                "Built-Time",
                now().atZoneSameInstant(of("UTC")).format(ofPattern("yyyy-MM-dd HH:mm:ss z")).toString(),
            )
        }
    }
    withType<Test> {
        // Set TEST_ENV environment variable for test execution
        environment["TEST_ENV"] = "hlæja"
        useJUnitPlatform()
    }
}
