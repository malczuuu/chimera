import com.diffplug.spotless.LineEnding
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    alias(libs.plugins.spotless)
}

subprojects {
    group = "io.github.malczuuu.chimera"

    // In order to avoid hardcoding snapshot versions, version is derived from the current Git commit hash. For CI/CD
    // add -Pversion={releaseVersion} parameter to match Git tag.
    version =
        if (version == "unspecified") {
            getSnapshotVersion(rootProject.rootDir)
        } else {
            version
        }

    // Configure Java 25 to all submodules that use java plugin.
    pluginManager.withPlugin("java") {
        configure<JavaPluginExtension> {
            toolchain.languageVersion = JavaLanguageVersion.of(25)
        }
    }

    // Usage:
    //   ./gradlew printVersion
    tasks.register("printVersion") {
        description = "Prints the current project version to the console"
        group = "help"
        doLast {
            println("${project.name} version: ${project.version}")
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Jar>().configureEach {
        manifest {
            attributes(mapOf("Implementation-Version" to project.version))
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed", "standardOut", "standardError")
            exceptionFormat = TestExceptionFormat.SHORT
            showStandardStreams = true
        }

        // for resolving warnings from mockito
        jvmArgs("-XX:+EnableDynamicAgentLoading")

        systemProperty("user.language", "en")
        systemProperty("user.country", "US")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

spotless {
    format("misc") {
        target("**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    java {
        target("**/src/**/*.java")

        googleJavaFormat("1.28.0")
        forbidWildcardImports()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    sql {
        target("**/src/main/resources/**/*.sql")

        dbeaver()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlin {
        target("**/src/**/*.kt")

        ktfmt("0.59").metaStyle()
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    kotlinGradle {
        target("**/*.gradle.kts")

        ktlint("1.8.0").editorConfigOverride(mapOf("max_line_length" to "120"))
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }
}
