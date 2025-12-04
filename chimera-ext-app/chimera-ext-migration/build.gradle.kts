import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java-library")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
    withSourcesJar()
}

dependencies {
    api(platform(project(":chimera-app:chimera-bom")))

    api(project(":chimera-app:chimera-migration"))

    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
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
