package io.github.malczuuu.chimera.app.service;

import io.github.malczuuu.chimera.core.ChimeraCoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = {ChimeraCoreAutoConfiguration.class})
@EnableJpaRepositories(basePackageClasses = {ChimeraCoreAutoConfiguration.class})
public class ServiceApplication {

  static void main(String[] args) {
    SpringApplication.run(ServiceApplication.class, args);
  }
}
