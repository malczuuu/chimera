import chimera.Lombok.lombok

plugins {
    id("internal.java-library-convention")
}

dependencies {
    api(platform(platform(project(":chimera-app:chimera-bom"))))

    lombok(libs.lombok)

    api(libs.flyway.core)
    api(libs.flyway.database.postgresql)
    api(libs.spring.boot.starter.data.jdbc)
    api(libs.spring.boot.starter.test)
    api(libs.spring.boot.testcontainers)
    api(libs.testcontainers.junit.jupiter)
    api(libs.testcontainers.postgresql)
    api(libs.wiremock.spring.boot)

    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}
