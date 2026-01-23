import com.diffplug.spotless.LineEnding

plugins {
    alias(libs.plugins.spotless)
}

allprojects {
    group = "io.github.malczuuu.chimera"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

spotless {
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

    format("misc") {
        target("**/.gitattributes", "**/.gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }

    format("yaml") {
        target("**/src/**/*.yml", "**/src/**/*.yaml")

        trimTrailingWhitespace()
        leadingTabsToSpaces(2)
        endWithNewline()
        lineEndings = LineEnding.UNIX
    }
}

// Usage:
//   ./gradlew printVersion
tasks.register("printVersion") {
    description = "Prints the current project version to the console"
    group = "help"

    val projectName = project.name
    val projectVersion = project.version

    doLast {
        println("$projectName version: $projectVersion")
    }
}
