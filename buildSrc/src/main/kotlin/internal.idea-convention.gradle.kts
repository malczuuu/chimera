import org.jetbrains.gradle.ext.Application
import org.jetbrains.gradle.ext.Gradle
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext")
}

idea {
    project {
        settings {
            runConfigurations {
                create<Application>("launch chimera-app-flyway") {
                    mainClass = "io.github.malczuuu.chimera.app.flyway.FlywayApplication"
                    moduleName = "chimera.chimera-app.chimera-app-flyway.main"
                    workingDirectory = rootProject.rootDir.absolutePath
                    programParameters = ""
                }
                create<Application>("launch chimera-app-service") {
                    mainClass = "io.github.malczuuu.chimera.app.service.ServiceApplication"
                    moduleName = "chimera.chimera-app.chimera-app-service.main"
                    workingDirectory = rootProject.rootDir.absolutePath
                    programParameters = ""
                }
                create<Application>("launch chimera-ext-app-flyway") {
                    mainClass = "io.github.malczuuu.chimera.ext.app.flyway.ExtFlywayApplication"
                    moduleName = "chimera.chimera-ext-app.chimera-ext-app-flyway.main"
                    workingDirectory = rootProject.rootDir.absolutePath
                    programParameters = ""
                }
                create<Application>("launch chimera-ext-app-service") {
                    mainClass = "io.github.malczuuu.chimera.ext.app.service.ExtServiceApplication"
                    moduleName = "chimera.chimera-ext-app.chimera-ext-app-service.main"
                    workingDirectory = rootProject.rootDir.absolutePath
                    programParameters = ""
                }
                create<Gradle>("build project") {
                    taskNames = listOf("spotlessApply build")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("test project") {
                    taskNames = listOf("check --rerun-tasks")
                    projectPath = rootProject.rootDir.absolutePath
                }
                create<Gradle>("test project [with containers]") {
                    taskNames = listOf("check --rerun-tasks -Pcontainers.enabled")
                    projectPath = rootProject.rootDir.absolutePath
                }
            }
        }
    }
}
