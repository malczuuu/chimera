plugins {
    id("chimera.java-library-convention")
}

dependencies {
    testImplementation(platform(project(":chimera-app:chimera-bom")))

    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}
