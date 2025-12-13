plugins {
    `kotlin-dsl`
}

// This is for buildSrc module only. Current toolchain is set to Java 17 here as a minimum
// supported version by Gradle 9. Other modules can use higher Java version (e.g., 21 or 25). Local
// Java installation may also be different, as long as it's higher than 17.
java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:7.5.0.202512021534-r")
}
