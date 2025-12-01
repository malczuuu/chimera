plugins {
    id("java-platform")
    id("maven-publish")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform(libs.spring.boot.dependencies))

    constraints {
        api(project(":chimera-app:chimera-core"))
        api(project(":chimera-app:chimera-migration"))
        api(libs.problem4j.spring.webmvc)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["javaPlatform"])
        }
    }
}
