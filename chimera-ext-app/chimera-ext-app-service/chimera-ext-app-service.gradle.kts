import internal.Lombok.lombok

plugins {
    id("internal.spring-app-convention")
}

dependencies {
    implementation(platform(project(":chimera-app:chimera-bom")))

    lombok(libs.lombok)

    implementation(project(":chimera-app:chimera-core"))
    runtimeOnly(libs.micrometer.registry.prometheus)
    runtimeOnly(libs.postgresql)

    testImplementation(project(":chimera-app:chimera-testkit"))
    testImplementation(project(":chimera-ext-app:chimera-ext-migration"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.micrometer.registry.prometheus)
    testRuntimeOnly(libs.postgresql)
}
