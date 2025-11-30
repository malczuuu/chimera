package io.github.malczuuu.chimera.core.infrastructure.scheduler;

import io.github.malczuuu.chimera.core.domain.weather.WeatherProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration(proxyBeanMethods = false)
@EnableScheduling
public class SchedulerInfraConfiguration {

  @ConditionalOnBooleanProperty(name = "chimera.weather.scheduler.enabled", matchIfMissing = true)
  @ConditionalOnMissingBean(WeatherScheduler.class)
  @Bean
  public WeatherScheduler weatherScheduler(WeatherProcessor weatherProcessor) {
    return new WeatherScheduler(weatherProcessor);
  }
}
