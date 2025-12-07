package io.github.malczuuu.chimera.ext.app.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {ExtServiceApplication.class})
@EnableJpaRepositories(basePackageClasses = {ExtServiceApplication.class})
@SpringBootApplication
public class ExtServiceApplication {

  /**
   * The {@code main} method must be {@code public} because Spring Boot's {@code bootJar} task
   * auto-detects the main class via reflection. If this method is not public, Gradle might fail to
   * determine the entry point for the executable JAR.
   *
   * <p>Because this is a demo app that has option to change java version {@code gradle.properties},
   * let's keep it public.
   */
  public static void main(String[] args) {
    SpringApplication.run(ExtServiceApplication.class, args);
  }
}
