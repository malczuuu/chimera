plugins {
    `kotlin-dsl`
}

// This sets the Java toolchain used to run Gradle tasks (here Java 17). Thanks to the Foojay plugin, Gradle will
// automatically discover or download the required JDK if it's missing. This does NOT affect the Java version of the
// application code, which can be higher (e.g., Java 25).
java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:7.4.0.202509020913-r")
}
