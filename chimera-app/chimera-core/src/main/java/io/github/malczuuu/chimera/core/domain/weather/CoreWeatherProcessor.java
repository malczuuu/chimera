package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.parameter.SettingsService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreWeatherProcessor implements WeatherProcessor {

  private static final Logger log = LoggerFactory.getLogger(CoreWeatherProcessor.class);

  private final WeatherService weatherService;
  private final WeatherClient weatherClient;
  private final SettingsService settingsService;

  public CoreWeatherProcessor(
      WeatherService weatherService, WeatherClient weatherClient, SettingsService settingsService) {
    this.weatherService = weatherService;
    this.weatherClient = weatherClient;
    this.settingsService = settingsService;
  }

  @Override
  public long executeWeatherSync() {
    List<WeatherModel> weathers =
        settingsService.findTrackedCities().stream()
            .flatMap(city -> weatherClient.getWeather(city).stream().peek(this::logWeather))
            .toList();

    weatherService.saveWeathers(weathers);

    return weathers.size();
  }

  private void logWeather(WeatherModel w) {
    log.info("Fetched weather for city={}; data={}", w.getCity(), w);
  }
}
