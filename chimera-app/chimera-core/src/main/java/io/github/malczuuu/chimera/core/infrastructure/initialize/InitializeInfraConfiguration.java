package io.github.malczuuu.chimera.core.infrastructure.initialize;

import io.github.malczuuu.chimera.core.domain.parameter.SettingsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class InitializeInfraConfiguration {

  @Bean
  public SettingsInitializer settingsInitializer(
      SettingsService settingsService,
      @Value("${chimera.initializer.tracked-cities:}") String trackedCities) {
    return new SettingsInitializer(settingsService, trackedCities);
  }
}
