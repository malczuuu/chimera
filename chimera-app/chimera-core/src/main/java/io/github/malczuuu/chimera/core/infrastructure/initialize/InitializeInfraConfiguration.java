package io.github.malczuuu.chimera.core.infrastructure.initialize;

import io.github.malczuuu.chimera.core.domain.parameter.SettingsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
    value = "chimera.initializer.enabled",
    havingValue = "true",
    matchIfMissing = true)
@Configuration(proxyBeanMethods = false)
public class InitializeInfraConfiguration {

  @ConditionalOnMissingBean(SettingsInitializer.class)
  @Bean
  public SettingsInitializer settingsInitializer(
      SettingsService settingsService,
      @Value("${chimera.initializer.tracked-cities:}") String trackedCities) {
    return new SettingsInitializer(settingsService, trackedCities);
  }
}
