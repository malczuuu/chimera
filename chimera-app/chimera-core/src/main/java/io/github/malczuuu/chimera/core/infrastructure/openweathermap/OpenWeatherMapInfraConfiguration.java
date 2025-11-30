package io.github.malczuuu.chimera.core.infrastructure.openweathermap;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogService;
import io.github.malczuuu.chimera.core.domain.weather.WeatherClient;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.client.OpenWeatherMapClient;
import java.time.Clock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OpenWeatherMapProperties.class)
public class OpenWeatherMapInfraConfiguration {

  @ConditionalOnMissingBean(WeatherClient.class)
  @Bean
  public OpenWeatherMapClient openWeatherMapClient(
      RestOperations restOperations,
      OpenWeatherMapProperties properties,
      IntegrationLogService integrationLogService,
      ObjectMapper objectMapper,
      Clock clock) {
    return new OpenWeatherMapClient(
        restOperations, properties, integrationLogService, objectMapper, clock);
  }
}
