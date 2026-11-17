plugins {
    id("konf.kotlin-library")
    id("konf.jmh")
}

dependencies {
    jmhImplementation(kotlin("stdlib"))
}
