plugins {
    id("konf.kotlin-library")
}

val snippet = sourceSets.create("snippet")

configurations.named(snippet.implementationConfigurationName) {
    extendsFrom(configurations.implementation.get())
}

dependencies {
    for (name in listOf(
        ":konf-core",
        ":konf-git",
        ":konf-hocon",
        ":konf-toml",
        ":konf-xml",
        ":konf-yaml",
    )) {
        api(project(name))
        testImplementation(testFixtures(project(name)))
    }

    snippet.implementationConfigurationName(sourceSets.main.get().output)
    testImplementation(snippet.output)
}
