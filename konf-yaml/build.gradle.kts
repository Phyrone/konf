plugins {
    id("konf.kotlin-library")
}

dependencies {
    api(project(":konf-core"))
    implementation(libs.snakeyaml)

    testImplementation(testFixtures(project(":konf-core")))
}
