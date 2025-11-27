plugins {
    id("java-library")
}

dependencies {
    api(platform(project(":chimera-app:chimera-bom")))

    api(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.actuator)
    api(libs.spring.boot.starter.data.jpa)

    api(libs.problem4j.spring.webmvc)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)

    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.postgresql)

    testImplementation(libs.wiremock.spring.boot)

    testRuntimeOnly(libs.micrometer.registry.prometheus)
    testRuntimeOnly(libs.postgresql)
    testRuntimeOnly(libs.junit.platform.launcher)
}
