plugins {
    id("konf.kotlin-library")
}

dependencies {
    api(project(":konf-core"))
    implementation(libs.graal.polyglot)
    implementation(libs.graal.js)

    testImplementation(testFixtures(project(":konf-core")))
}
