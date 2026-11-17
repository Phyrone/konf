plugins {
    alias(libs.plugins.versions)
    alias(libs.plugins.task.tree)
}

allprojects {
    group = "de.phyrone"
    // Development version; CI overrides it with -Pkonf.version=<tag> for releases.
    version = providers.gradleProperty("konf.version").get()
}

tasks.wrapper {
    gradleVersion = "9.9.1"
    distributionType = Wrapper.DistributionType.BIN
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
