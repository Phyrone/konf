plugins {
    id("konf.kotlin-library")
}

dependencies {
    api(project(":konf-core"))
    api(libs.jgit)

    testImplementation(testFixtures(project(":konf-core")))
}
