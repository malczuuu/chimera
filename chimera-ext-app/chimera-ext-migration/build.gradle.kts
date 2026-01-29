plugins {
    id("chimera.java-library-convention")
}

dependencies {
    api(platform(project(":chimera-app:chimera-bom")))

    api(project(":chimera-app:chimera-migration"))

    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}
