plugins {
    id("konf.kotlin-library")
}

dependencies {
    api(project(":konf-core"))
    implementation(libs.typesafe.config)

    testImplementation(testFixtures(project(":konf-core")))
}
