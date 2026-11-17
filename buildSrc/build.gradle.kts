plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradlePlugin.kotlin)
    implementation(libs.gradlePlugin.kotlin.allopen)
    implementation(libs.gradlePlugin.dokka)
    implementation(libs.gradlePlugin.spotless)
    implementation(libs.gradlePlugin.jmh)
}
