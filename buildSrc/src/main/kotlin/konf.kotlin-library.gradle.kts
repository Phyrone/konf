import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    `java-library`
    `java-test-fixtures`
    id("org.jetbrains.kotlin.jvm")
    id("com.diffplug.spotless")
    id("konf.publishing")
}

val javaVersion = libs.version("java").toInt()

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

kotlin {
    jvmToolchain(javaVersion)
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(javaVersion.toString())
    }
}

configurations {
    testFixturesImplementation { extendsFrom(implementation.get()) }
    testImplementation { extendsFrom(testFixturesImplementation.get()) }
}

dependencies {
    api(libs.lib("kotlinx-coroutines-core"))
    api(platform(libs.lib("jackson-bom")))
    api(libs.lib("jackson-core"))
    api(libs.lib("jackson-annotations"))
    api(libs.lib("jackson-databind"))
    implementation(libs.lib("jackson-module-kotlin"))
    implementation(libs.lib("jackson-datatype-jsr310"))
    implementation(libs.lib("kotlin-reflect"))
    implementation(libs.lib("reflections"))
    implementation(libs.lib("commons-text"))

    testFixturesImplementation(libs.lib("kotlin-test"))
    testFixturesImplementation(libs.lib("hamkrest"))
    testFixturesImplementation(libs.lib("hamcrest-all"))
    testFixturesImplementation(libs.lib("spek-api"))
    testFixturesImplementation(libs.lib("spek-data-driven"))
    testFixturesImplementation(libs.lib("spek-subject"))

    testImplementation(platform(libs.lib("junit-bom")))
    testImplementation(libs.lib("junit-jupiter-api"))
    testImplementation(libs.lib("spark-core"))
    testRuntimeOnly(libs.lib("junit-platform-launcher"))
    testRuntimeOnly(libs.lib("junit-jupiter-engine"))
    testRuntimeOnly(libs.lib("spek-engine"))
    testRuntimeOnly(libs.lib("slf4j-simple"))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

val envProperties = rootProject.layout.projectDirectory
    .file("konf-core/src/test/kotlin/com/uchuhimo/konf/source/env/env.properties")

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
    systemProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn")
    systemProperty("junit.jupiter.execution.parallel.enabled", true)
    systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
    inputs.file(envProperties)
    val properties = Properties().apply { envProperties.asFile.inputStream().use { load(it) } }
    properties.forEach { key, value -> environment(key as String, value) }
}

spotless {
    java {
        googleJavaFormat(libs.version("googleJavaFormat"))
        trimTrailingWhitespace()
        endWithNewline()
        licenseHeaderFile(rootProject.file("config/spotless/apache-license-2.0.java"))
    }
    kotlin {
        ktlint(libs.version("ktlint"))
        trimTrailingWhitespace()
        endWithNewline()
        licenseHeaderFile(rootProject.file("config/spotless/apache-license-2.0.kt"))
    }
}
