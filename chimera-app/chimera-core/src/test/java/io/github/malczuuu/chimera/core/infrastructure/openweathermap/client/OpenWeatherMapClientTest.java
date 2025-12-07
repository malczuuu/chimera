package io.github.malczuuu.chimera.core.infrastructure.openweathermap.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.malczuuu.chimera.core.CoreTestApplication;
import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogEntity;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogRepository;
import io.github.malczuuu.chimera.core.domain.weather.WeatherClient;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.client.WeatherResponse.Main;
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
@SpringBootTest(classes = {CoreTestApplication.class})
@PostgresContainerTest
class OpenWeatherMapClientTest {

  @Autowired private WeatherClient weatherClient;

  @Autowired private IntegrationLogRepository integrationLogRepository;

  @MockitoBean private RestOperations restOperations;

  @BeforeEach
  void beforeEach() {
    integrationLogRepository.deleteAll();
  }

  @Test
  void givenSuccessfulResponse_whenGetWeather_thenReturnsWeatherModel() {
    WeatherResponse response = createWeatherResponse("Krakow", 15.5, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isPresent();
    assertThat(result.get().getCity()).isEqualTo("Krakow");
    assertThat(result.get().getTemperature()).isEqualTo(15.5);
    assertThat(integrationLogRepository.count()).isEqualTo(1);
  }

  @Test
  void givenSuccessfulResponse_whenGetWeather_thenLogsIntegrationWithCorrectData() {
    WeatherResponse response = createWeatherResponse("Warsaw", 18.0, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    weatherClient.getWeather("Warsaw");

    assertThat(integrationLogRepository.count()).isEqualTo(1);
    IntegrationLogEntity log = integrationLogRepository.findAll().get(0);
    assertThat(log.getLabel()).isEqualTo("weather");
    assertThat(log.getProtocol()).isEqualTo("http");
    assertThat(log.getDirection()).isEqualTo("out");
    assertThat(log.getResponseStatus()).isEqualTo("200 OK");
    assertThat(log.getRequestTimestamp()).isNotNull();
    assertThat(log.getResponseBody()).isNotNull();
  }

  @Test
  void givenEmptyResponseBody_whenGetWeather_thenReturnsEmpty() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isEmpty();
    assertThat(integrationLogRepository.count()).isEqualTo(1);
  }

  @Test
  void givenRestClientException_whenGetWeather_thenReturnsEmptyAndLogsError() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenThrow(new RestClientException("Connection timeout"));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isEmpty();
    assertThat(integrationLogRepository.count()).isEqualTo(1);
    IntegrationLogEntity log = integrationLogRepository.findAll().get(0);
    assertThat(log.getRequestAttributes()).contains("RestClientException");
    assertThat(log.getRequestAttributes()).contains("Connection timeout");
  }

  @Test
  void givenGenericException_whenGetWeather_thenReturnsEmptyAndLogsError() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenThrow(new RuntimeException("Unexpected error"));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isEmpty();
    assertThat(integrationLogRepository.count()).isEqualTo(1);
    IntegrationLogEntity log = integrationLogRepository.findAll().get(0);
    assertThat(log.getRequestAttributes()).contains("RuntimeException");
    assertThat(log.getRequestAttributes()).contains("Unexpected error");
  }

  @Test
  void givenResponseWithNullMain_whenGetWeather_thenReturnsWeatherWithNullTemperature() {
    WeatherResponse response = createWeatherResponseWithNullMain("Krakow", 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Krakow");

    assertThat(result).isPresent();
    assertThat(result.get().getCity()).isEqualTo("Krakow");
    assertThat(result.get().getTemperature()).isNull();
  }

  @Test
  void givenValidResponse_whenGetWeather_thenConvertsTimestampCorrectly() {
    long epochSeconds = 1733306400L;
    WeatherResponse response = createWeatherResponse("Gdansk", 12.0, epochSeconds);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    Optional<WeatherModel> result = weatherClient.getWeather("Gdansk");

    assertThat(result).isPresent();
    assertThat(result.get().getTimestamp().toEpochSecond()).isEqualTo(epochSeconds);
  }

  @Test
  void givenMultipleCalls_whenGetWeather_thenEachCallLogsIntegration() {
    WeatherResponse response1 = createWeatherResponse("City1", 10.0, 1733306400L);
    WeatherResponse response2 = createWeatherResponse("City2", 20.0, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response1, HttpStatus.OK))
        .thenReturn(new ResponseEntity<>(response2, HttpStatus.OK));

    weatherClient.getWeather("City1");
    weatherClient.getWeather("City2");

    assertThat(integrationLogRepository.count()).isEqualTo(2);
  }

  @Test
  void givenSuccessfulResponse_whenGetWeather_thenResponseBodyIsSerialized() {
    WeatherResponse response = createWeatherResponse("Krakow", 15.5, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    weatherClient.getWeather("Krakow");

    IntegrationLogEntity log = integrationLogRepository.findAll().get(0);
    assertThat(log.getResponseBody()).isNotNull();
    assertThat(log.getResponseBody()).contains("Krakow");
    assertThat(log.getResponseBody()).contains("15.5");
  }

  @Test
  void givenDifferentHttpStatus_whenGetWeather_thenLogsCorrectStatus() {
    WeatherResponse response = createWeatherResponse("Krakow", 15.5, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.CREATED));

    weatherClient.getWeather("Krakow");

    IntegrationLogEntity log = integrationLogRepository.findAll().get(0);
    assertThat(log.getResponseStatus()).isEqualTo("201 CREATED");
  }

  @Test
  void givenException_whenGetWeather_thenStillLogsIntegration() {
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenThrow(new RestClientException("Network error"));

    weatherClient.getWeather("Krakow");

    assertThat(integrationLogRepository.count()).isEqualTo(1);
  }

  @Test
  void givenValidCity_whenGetWeather_thenCallsRestOperations() {
    WeatherResponse response = createWeatherResponse("Krakow", 15.5, 1733306400L);
    when(restOperations.getForEntity(anyString(), eq(WeatherResponse.class), anyMap()))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

    weatherClient.getWeather("Krakow");

    verify(restOperations).getForEntity(anyString(), eq(WeatherResponse.class), anyMap());
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
