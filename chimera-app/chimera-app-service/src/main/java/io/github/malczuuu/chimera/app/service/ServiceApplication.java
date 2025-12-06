package io.github.malczuuu.chimera.app.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication {

  /**
   * The {@code main} method must be {@code public} because Spring Boot's {@code bootJar} task
   * auto-detects the main class via reflection. If this method is not public, Gradle might fail to
   * determine the entry point for the executable JAR.
   *
   * <p>Because this is a demo app that has option to change java version {@code gradle.properties},
   * let's keep it public.
   */
  public static void main(String[] args) {
    SpringApplication.run(ServiceApplication.class, args);
  }
}
