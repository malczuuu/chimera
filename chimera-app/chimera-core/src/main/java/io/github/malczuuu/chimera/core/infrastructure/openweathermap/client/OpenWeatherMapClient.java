package io.github.malczuuu.chimera.core.infrastructure.openweathermap.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel.IntegrationLogModelBuilder;
import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogService;
import io.github.malczuuu.chimera.core.domain.weather.WeatherClient;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.OpenWeatherMapProperties;
import java.time.Clock;
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

  private final IntegrationLogService integrationLogService;

  private final ObjectMapper objectMapper;

  private final Clock clock;

  public OpenWeatherMapClient(
      RestOperations restOperations,
      OpenWeatherMapProperties properties,
      IntegrationLogService integrationLogService,
      ObjectMapper objectMapper,
      Clock clock) {
    this.restOperations = restOperations;
    this.properties = properties;
    this.integrationLogService = integrationLogService;
    this.objectMapper = objectMapper;
    this.clock = clock;
  }

  @Override
  public Optional<WeatherModel> getWeather(String city) {
    IntegrationLogModelBuilder callLogBuilder = IntegrationLogModel.builder();

    String withQueryString =
        properties.getWeatherEndpoint() + "?q={city}&appid={appid}&units={units}&lang={lang}";
    Map<String, String> query =
        Map.of("city", city, "appid", properties.getToken(), "units", "metric", "lang", "en");

    try {
      callLogBuilder =
          callLogBuilder
              .label("weather")
              .protocol("http")
              .address(withQueryString)
              .requestTimestamp(clock.instant().atOffset(ZoneOffset.UTC))
              .direction("out");

      ResponseEntity<WeatherResponse> response =
          restOperations.getForEntity(withQueryString, WeatherResponse.class, query);

      callLogBuilder = callLogBuilder.responseStatus(response.getStatusCode().toString());

      if (response.getBody() == null) {
        log.warn("OpenWeatherMap API returned empty body for city={}", city);
        return Optional.empty();
      }

      callLogBuilder =
          callLogBuilder.responseBody(objectMapper.writeValueAsString(response.getBody()));

      return Optional.of(toWeatherModel(response.getBody()));
    } catch (Exception e) {
      callLogBuilder.requestAttributes(e.getClass().getSimpleName() + ": " + e.getMessage());
      log.error("Unable to fetch weather from OpenWeatherMap API; reason={}", e.getMessage(), e);
    } finally {
      integrationLogService.createIntegrationLog(callLogBuilder.build());
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
