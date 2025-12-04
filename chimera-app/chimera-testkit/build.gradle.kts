import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java-library")
    id("maven-publish")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
    withSourcesJar()
}

dependencies {
    api(platform(platform(project(":chimera-app:chimera-bom"))))

    lombok(libs.lombok)

    api(libs.flyway.core)
    api(libs.flyway.database.postgresql)
    api(libs.spring.boot.starter.data.jdbc)
    api(libs.spring.boot.starter.test)
    api(libs.spring.boot.testcontainers)
    api(libs.testcontainers.junit.jupiter)
    api(libs.testcontainers.postgresql)
    api(libs.wiremock.spring.boot)

    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Build-Jdk-Spec" to java.toolchain.languageVersion.get().toString(),
            "Created-By" to "Gradle ${gradle.gradleVersion}",
        )
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform {
        if (project.findProperty("containers.enabled")?.toString() == "false") {
            excludeTags("testcontainers")
        }
    }

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
        exceptionFormat = TestExceptionFormat.SHORT
        showStandardStreams = true
    }

    // For resolving warnings from mockito.
    jvmArgs("-XX:+EnableDynamicAgentLoading")

    systemProperty("user.language", "en")
    systemProperty("user.country", "US")
}
