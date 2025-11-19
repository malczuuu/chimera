package io.github.malczuuu.chimera.core.infrastructure.openweathermap.client;

import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.weather.WeatherClient;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.OpenWeatherMapProperties;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

public class OpenWeatherMapClient implements WeatherClient {

  private static final Logger log = LoggerFactory.getLogger(OpenWeatherMapClient.class);

  private final RestOperations restOperations;
  private final OpenWeatherMapProperties properties;

  public OpenWeatherMapClient(RestOperations restOperations, OpenWeatherMapProperties properties) {
    this.restOperations = restOperations;
    this.properties = properties;
  }

  @Override
  public Optional<WeatherModel> getWeather(String city) {
    String withQueryString =
        properties.getWeatherEndpoint() + "?q={city}&appid={appid}&units={units}&lang={lang}";
    Map<String, String> query =
        Map.of("city", city, "appid", properties.getToken(), "units", "metric", "lang", "en");

    try {
      ResponseEntity<WeatherResponse> response =
          restOperations.getForEntity(withQueryString, WeatherResponse.class, query);

      if (response.getBody() == null) {
        log.warn("OpenWeatherMap API returned empty body for city={}", city);
        return Optional.empty();
      }

      return Optional.of(toWeatherModel(response.getBody()));
    } catch (Exception e) {
      log.error("Unable to fetch weather from OpenWeatherMap API; reason={}", e.getMessage(), e);
    }
    return Optional.empty();
  }

  private WeatherModel toWeatherModel(WeatherResponse response) {
    return new WeatherModel(
        response.name(),
        response.main() != null ? response.main().temp() : null,
        "",
        List.of(),
        Instant.ofEpochSecond(response.dt()).atOffset(ZoneOffset.UTC));
  }
}
