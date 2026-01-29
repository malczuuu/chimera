plugins {
    id("chimera.java-convention")
    id("org.springframework.boot")
}

tasks.withType<Jar>().configureEach {
    if (name != "bootJar") {
        enabled = false
    }
}
