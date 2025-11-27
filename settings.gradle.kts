pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

rootProject.name = "chimera"

include(":chimera-app:chimera-app-service")
include(":chimera-app:chimera-bom")
include(":chimera-app:chimera-core")
