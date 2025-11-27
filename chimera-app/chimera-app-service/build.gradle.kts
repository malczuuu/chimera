plugins {
    id("java")
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(platform(project(":chimera-app:chimera-bom")))

    implementation(project(":chimera-app:chimera-core"))

    runtimeOnly(libs.micrometer.registry.prometheus)
    runtimeOnly(libs.postgresql)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.postgresql)

    testRuntimeOnly(libs.micrometer.registry.prometheus)
    testRuntimeOnly(libs.postgresql)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
