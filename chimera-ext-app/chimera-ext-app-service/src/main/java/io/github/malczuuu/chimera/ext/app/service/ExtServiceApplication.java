package io.github.malczuuu.chimera.ext.app.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackageClasses = {ExtServiceApplication.class})
@EnableJpaRepositories(basePackageClasses = {ExtServiceApplication.class})
@SpringBootApplication
public class ExtServiceApplication {

  static void main(String[] args) {
    SpringApplication.run(ExtServiceApplication.class, args);
  }
}
