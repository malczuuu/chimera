import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    alias(libs.plugins.spring.boot)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

dependencies {
    implementation(platform(project(":chimera-app:chimera-bom")))

    implementation(project(":chimera-app:chimera-migration"))
    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.spring.boot.starter.data.jdbc)
    runtimeOnly(libs.postgresql)

    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Jar>().configureEach {
    if (name != "bootJar") {
        enabled = false
    }

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
    useJUnitPlatform()

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
