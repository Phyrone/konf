pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}

rootProject.name = "konf"

include(
    "konf-core",
    "konf-git",
    "konf-hocon",
    "konf-js",
    "konf-toml",
    "konf-xml",
    "konf-yaml",
    "konf-all",
)
