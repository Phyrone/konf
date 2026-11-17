plugins {
    alias(libs.plugins.versions)
    alias(libs.plugins.task.tree)
}

allprojects {
    group = "de.phyrone"
    version = "1.2.6"
}

tasks.wrapper {
    gradleVersion = "9.9.1"
    distributionType = Wrapper.DistributionType.ALL
}

tasks.dependencyUpdates {
    revision = "release"
    outputFormatter = "plain"
    rejectVersionIf {
        listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "eap", "pr", "dev", "mt")
            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
            .any { it.matches(candidate.version) }
    }
}
