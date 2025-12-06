import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.jvm.toolchain.JavaLanguageVersion

/**
 * Returns the Java language version for the given [project] as a [Provider]. Reads the version from
 * the Gradle property `project.java.version`.
 */
fun getProjectJavaVersion(project: Project): Provider<JavaLanguageVersion> {
  return project.providers.gradleProperty("project.java.version").map { JavaLanguageVersion.of(it) }
}
