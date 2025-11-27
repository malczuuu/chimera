package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.domain.parameter.SettingsService;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration(proxyBeanMethods = false)
public class WeatherDomainConfiguration {

  @ConditionalOnMissingBean(WeatherFactory.class)
  @Bean
  public WeatherFactory weatherFactory() {
    return new CoreWeatherFactory();
  }

  @ConditionalOnMissingBean(WeatherRepository.class)
  @Bean
  public WeatherRepository weatherRepository(EntityManager entityManager) {
    return new JpaRepositoryFactory(entityManager).getRepository(WeatherRepository.class);
  }

  @ConditionalOnMissingBean(WeatherService.class)
  @Bean
  public WeatherService weatherService(
      WeatherFactory weatherFactory, WeatherRepository weatherRepository) {
    return new CoreWeatherService(weatherFactory, weatherRepository);
  }

  @ConditionalOnMissingBean(WeatherProcessor.class)
  @Bean
  public WeatherProcessor weatherProcessor(
      WeatherService weatherService, WeatherClient weatherClient, SettingsService settingsService) {
    return new CoreWeatherProcessor(weatherService, weatherClient, settingsService);
  }
}
