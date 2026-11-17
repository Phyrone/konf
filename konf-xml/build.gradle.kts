plugins {
    id("konf.kotlin-library")
}

dependencies {
    api(project(":konf-core"))
    implementation(libs.dom4j)
    implementation(libs.jaxen)

    testImplementation(testFixtures(project(":konf-core")))
}
