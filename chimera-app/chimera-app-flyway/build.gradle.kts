plugins {
    id("java")
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(platform(project(":chimera-app:chimera-bom")))

    implementation(project(":chimera-app:chimera-migration"))
    implementation(libs.flyway.core)
    implementation(libs.flyway.database.postgresql)
    implementation(libs.spring.boot.starter.data.jdbc)
    runtimeOnly(libs.postgresql)

    testImplementation(project(":chimera-app:chimera-testkit"))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.postgresql)
}
