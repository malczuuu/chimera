package io.github.malczuuu.chimera.core.infrastructure.rest;

import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogService;
import io.github.malczuuu.chimera.core.domain.weather.WeatherService;
import io.github.malczuuu.chimera.core.infrastructure.rest.controller.IntegrationLogController;
import io.github.malczuuu.chimera.core.infrastructure.rest.controller.WeatherController;
import io.github.malczuuu.chimera.core.infrastructure.rest.problem.LoggingAdviceMvcInspector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration(proxyBeanMethods = false)
public class RestInfraConfiguration {

  @ConditionalOnMissingBean(RestOperations.class)
  @Bean
  public RestOperations restOperations(RestTemplateBuilder builder) {
    return builder.build();
  }

  @ConditionalOnMissingBean(IntegrationLogController.class)
  @Bean
  public IntegrationLogController integrationLogController(
      IntegrationLogService integrationLogService) {
    return new IntegrationLogController(integrationLogService);
  }

  @ConditionalOnMissingBean(WeatherController.class)
  @Bean
  public WeatherController weatherController(WeatherService weatherService) {
    return new WeatherController(weatherService);
  }

  @ConditionalOnMissingBean(LoggingAdviceMvcInspector.class)
  @Bean
  public LoggingAdviceMvcInspector LoggingAdviceMvcInspector() {
    return new LoggingAdviceMvcInspector();
  }
}
