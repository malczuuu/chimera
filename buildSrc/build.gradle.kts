plugins {
    `kotlin-dsl`
}

// This is for buildSrc module only. Current toolchain is set to Java 17 here as a minimum
// supported version. Gradle 9 supports running and compiling on Java 17+, but team members may
// have newer JVMs installed (e.g., 21 or 25). This ensures compatibility while allowing to compile
// with any local JDK.
java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:7.4.0.202509020913-r")
}
