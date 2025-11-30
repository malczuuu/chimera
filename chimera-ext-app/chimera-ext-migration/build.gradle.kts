plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    api(platform(project(":chimera-app:chimera-bom")))

    api(project(":chimera-app:chimera-migration"))

    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}
