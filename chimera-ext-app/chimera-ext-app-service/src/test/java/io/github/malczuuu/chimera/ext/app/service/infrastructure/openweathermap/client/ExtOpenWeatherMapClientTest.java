package io.github.malczuuu.chimera.ext.app.service.infrastructure.openweathermap.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.weather.WeatherClient;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.client.WeatherResponse;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.client.WeatherResponse.Main;
import io.github.malczuuu.chimera.ext.app.service.ExtServiceApplication;
import io.github.malczuuu.chimera.ext.app.service.domain.integration.ExtIntegrationLogEntity;
import io.github.malczuuu.chimera.ext.app.service.domain.integration.ExtIntegrationLogRepository;
import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {ExtServiceApplication.class})
@PostgresContainerTest
class ExtOpenWeatherMapClientTest {

  @Autowired private WeatherClient weatherClient;

  @Autowired private ExtIntegrationLogRepository integrationLogRepository;

  @MockitoBean private RestOperations restOperations;

  @BeforeEach
  void beforeEach() {
    integrationLogRepository.deleteAll();
  }

  @Test
  void givenSuccessfulResponse_whenGetWeather_thenLogsIntegrationWithTraceId() {
    WeatherResponse response = createWeatherResponse("Krakow", 15.5, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isPresent();
    assertThat(integrationLogRepository.count()).isEqualTo(1);
    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getTraceId()).isNotNull();
    assertThat(log.getTraceId())
        .matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
  }

  @Test
  void givenSuccessfulResponse_whenGetWeather_thenExtIntegrationLogHasAllCoreFields() {
    WeatherResponse response = createWeatherResponse("Warsaw", 18.0, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    weatherClient.getWeather("Warsaw");

    assertThat(integrationLogRepository.count()).isEqualTo(1);
    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getLabel()).isEqualTo("weather");
    assertThat(log.getProtocol()).isEqualTo("http");
    assertThat(log.getDirection()).isEqualTo("out");
    assertThat(log.getResponseStatus()).isEqualTo("200 OK");
    assertThat(log.getRequestTimestamp()).isNotNull();
    assertThat(log.getResponseBody()).isNotNull();
    assertThat(log.getTraceId()).isNotNull();
  }

  @Test
  void givenMultipleCalls_whenGetWeather_thenEachLogHasUniqueTraceId() {
    WeatherResponse response1 = createWeatherResponse("City1", 10.0, 1733306400L);
    WeatherResponse response2 = createWeatherResponse("City2", 20.0, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response1, HttpStatus.OK))
        .thenReturn(new ResponseEntity<>(response2, HttpStatus.OK));

    weatherClient.getWeather("City1");
    weatherClient.getWeather("City2");

    assertThat(integrationLogRepository.count()).isEqualTo(2);
    List<ExtIntegrationLogEntity> logs =
        integrationLogRepository.findAll().stream()
            .map(log -> (ExtIntegrationLogEntity) log)
            .toList();
    String traceId1 = logs.get(0).getTraceId();
    String traceId2 = logs.get(1).getTraceId();
    assertThat(traceId1).isNotNull().isNotEqualTo(traceId2);
  }

  @Test
  void givenException_whenGetWeather_thenStillLogsWithTraceId() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenThrow(new RestClientException("Connection timeout"));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isEmpty();
    assertThat(integrationLogRepository.count()).isEqualTo(1);
    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getTraceId()).isNotNull();
    assertThat(log.getRequestAttributes()).contains("RestClientException");
    assertThat(log.getRequestAttributes()).contains("Connection timeout");
  }

  @Test
  void givenSuccessfulResponse_whenGetWeather_thenTraceIdIsValidUUID() {
    WeatherResponse response = createWeatherResponse("Gdansk", 12.0, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    weatherClient.getWeather("Gdansk");

    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    String traceId = log.getTraceId();
    assertThat(traceId).isNotNull();
    assertThat(traceId.split("-")).hasSize(5);
    assertThat(traceId).hasSize(36);
  }

  @Test
  void givenEmptyResponse_whenGetWeather_thenStillLogsWithTraceId() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isEmpty();
    assertThat(integrationLogRepository.count()).isEqualTo(1);
    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getTraceId()).isNotNull();
  }

  @Test
  void givenResponseWithNullMain_whenGetWeather_thenLogsWithTraceId() {
    WeatherResponse response = createWeatherResponseWithNullMain("Krakow", 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isPresent();
    assertThat(result.get().getTemperature()).isNull();
    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getTraceId()).isNotNull();
  }

  @Test
  void givenDifferentHttpStatus_whenGetWeather_thenLogsCorrectStatusAndTraceId() {
    WeatherResponse response = createWeatherResponse("Krakow", 15.5, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));

    weatherClient.getWeather("Krakow");

    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getResponseStatus()).isEqualTo("201 CREATED");
    assertThat(log.getTraceId()).isNotNull();
  }

  @Test
  void givenGenericException_whenGetWeather_thenLogsErrorWithTraceId() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenThrow(new RuntimeException("Unexpected error"));

    Optional<WeatherModel> result = weatherClient.getWeather("TestCity");

    assertThat(result).isEmpty();
    ExtIntegrationLogEntity log =
        (ExtIntegrationLogEntity) integrationLogRepository.findAll().getFirst();
    assertThat(log.getTraceId()).isNotNull();
    assertThat(log.getRequestAttributes()).contains("RuntimeException");
    assertThat(log.getRequestAttributes()).contains("Unexpected error");
  }

  private WeatherResponse createWeatherResponse(String city, Double temp, Long dt) {
    Main main = new Main(temp, temp - 2.0, temp - 1.0, temp + 1.0, 1013, 65, null, null);
    return new WeatherResponse(
        null, List.of(), "stations", main, 10000, null, null, dt, null, 0, 123456L, city, 200);
  }

  private WeatherResponse createWeatherResponseWithNullMain(String city, Long dt) {
    return new WeatherResponse(
        null, List.of(), "stations", null, 10000, null, null, dt, null, 0, 123456L, city, 200);
  }
}
