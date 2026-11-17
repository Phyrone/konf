import java.net.URI

plugins {
    java
    `maven-publish`
    signing
    id("org.jetbrains.dokka")
}

val projectUrl = "https://github.com/uchuhimo/konf"

java {
    withSourcesJar()
}

dokka {
    dokkaSourceSets.configureEach {
        jdkVersion = libs.version("java").toInt()
        reportUndocumented = false
        sourceLink {
            localDirectory = projectDir
            remoteUrl = URI("$projectUrl/blob/v${project.version}/")
            remoteLineSuffix = "#L"
        }
    }
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier = "javadoc"
    from(tasks.dokkaGeneratePublicationHtml)
}

val repositoryUrl = privateProperty("repositoryUrl")

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
            artifact(javadocJar)
            artifactId = if (project.name == "konf-all") "konf" else project.name

            suppressPomMetadataWarningsFor("testFixturesApiElements")
            suppressPomMetadataWarningsFor("testFixturesRuntimeElements")
            pom {
                name = rootProject.name
                description = "A type-safe cascading configuration library for Kotlin/Java, " +
                    "supporting most configuration formats"
                url = projectUrl
                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "uchuhimo"
                        name = "uchuhimo"
                        email = "uchuhimo@outlook.com"
                    }
                }
                scm {
                    url = projectUrl
                }
            }
        }
    }
    repositories {
        if (repositoryUrl.get().isNotEmpty()) {
            maven {
                url = uri(repositoryUrl.get())
                credentials {
                    username = privateProperty("repoUserToken").get()
                    password = privateProperty("repoUserPassword").get()
                }
            }
        }
    }
}

signing {
    isRequired = privateProperty("signPublications").get() == "true"
    sign(publishing.publications["maven"])
}

tasks.register("install") {
    group = "publishing"
    description = "Publishes all publications to the local Maven repository."
    dependsOn(tasks.publishToMavenLocal)
}

tasks.publish {
    dependsOn(tasks.check)
}
