import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.lib(alias: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(alias).orElseThrow { IllegalArgumentException("Unknown library alias '$alias'") }

internal fun VersionCatalog.version(alias: String): String =
    findVersion(alias).orElseThrow { IllegalArgumentException("Unknown version alias '$alias'") }.requiredVersion

/**
 * Reads a publishing setting from Gradle properties (`konf.<key>`, e.g. in ~/.gradle/gradle.properties
 * or ORG_GRADLE_PROJECT_konf.<key>), falling back to the legacy untracked `private.properties` file.
 */
internal fun Project.privateProperty(key: String): Provider<String> {
    val legacy = providers.fileContents(rootProject.layout.projectDirectory.file("private.properties"))
        .asText
        .map { text -> Properties().apply { load(text.reader()) }.getProperty(key).orEmpty() }
    return providers.gradleProperty("konf.$key").orElse(legacy).orElse("")
}
