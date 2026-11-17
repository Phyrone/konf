plugins {
    id("konf.kotlin-library")
}

dependencies {
    api(project(":konf-core"))
    implementation(libs.toml4j)

    testImplementation(testFixtures(project(":konf-core")))
}
