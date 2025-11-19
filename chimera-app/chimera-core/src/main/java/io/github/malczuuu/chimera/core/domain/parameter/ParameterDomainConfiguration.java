package io.github.malczuuu.chimera.core.domain.parameter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ParameterDomainConfiguration {

  @ConditionalOnMissingBean(ParameterFactory.class)
  @Bean
  public ParameterFactory parameterFactory() {
    return new CoreParameterFactory();
  }

  @ConditionalOnMissingBean(ParameterService.class)
  @Bean
  public ParameterService parameterService(
      ParameterFactory parameterFactory, ParameterRepository parameterRepository) {
    return new CoreParameterService(parameterFactory, parameterRepository);
  }

  @ConditionalOnMissingBean(SettingsService.class)
  @Bean
  public SettingsService settingsService(ParameterService parameterService) {
    return new CoreSettingsService(parameterService);
  }
}
