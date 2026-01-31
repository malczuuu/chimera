import chimera.Lombok.lombok

plugins {
    id("chimera.java-library-convention")
}

dependencies {
    api(platform(platform(project(":chimera-app:chimera-bom"))))

    lombok(libs.lombok)

    api(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.actuator)
    api(libs.spring.boot.starter.data.jpa)
    api(libs.springdoc.openapi.starter.webmvc.ui)
    api(libs.problem4j.spring.webmvc)

    testImplementation(project(":chimera-app:chimera-migration"))
    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.micrometer.registry.prometheus)
    testRuntimeOnly(libs.postgresql)
}
