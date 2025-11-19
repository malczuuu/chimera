package io.github.malczuuu.chimera.core.infrastructure.scheduler;

import io.github.malczuuu.chimera.core.domain.weather.WeatherProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherScheduler {

  private static final Logger log = LoggerFactory.getLogger(WeatherScheduler.class);

  private final WeatherProcessor weatherProcessor;

  public WeatherScheduler(WeatherProcessor weatherProcessor) {
    this.weatherProcessor = weatherProcessor;
  }

  @Scheduled(
      initialDelayString = "${weather.scheduler.initial-delay}",
      fixedDelayString = "${weather.scheduler.fixed-delay}")
  public void fetchWeather() {
    Long count = weatherProcessor.executeWeatherSync();
    log.info("Synchronized count={} weather records", count);
  }
}
