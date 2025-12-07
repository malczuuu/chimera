package io.github.malczuuu.chimera.core.domain.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.malczuuu.chimera.core.CoreTestApplication;
import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.parameter.SettingsService;
import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {CoreTestApplication.class})
@PostgresContainerTest
class CoreWeatherProcessorTest {

  @Autowired private WeatherProcessor weatherProcessor;

  @Autowired private WeatherRepository weatherRepository;

  @Autowired private SettingsService settingsService;

  @MockitoBean private WeatherClient weatherClient;

  @BeforeEach
  void beforeEach() {
    weatherRepository.deleteAll();
  }

  @AfterEach
  void afterEach(@Value("${chimera.initializer.tracked-cities}") String trackedCities) {
    settingsService.setTrackedCities(trackedCities.split(","));
  }

  @Test
  void givenNoTrackedCities_whenExecuteWeatherSync_thenReturnsZero() {
    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isZero();
    assertThat(weatherRepository.count()).isZero();
  }

  @Test
  void givenOneTrackedCity_whenExecuteWeatherSync_thenFetchesAndSavesWeather() {
    settingsService.setTrackedCities("Krakow");
    WeatherModel weather = createWeatherModel("Krakow", 15.5);
    when(weatherClient.getWeather("Krakow")).thenReturn(Optional.of(weather));

    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isEqualTo(1);
    verify(weatherClient).getWeather("Krakow");
    assertThat(weatherRepository.count()).isEqualTo(1);
    WeatherEntity saved = weatherRepository.findAll().get(0);
    assertThat(saved.getCity()).isEqualTo("Krakow");
    assertThat(saved.getTemperature()).isEqualTo(15.5);
  }

  @Test
  void givenMultipleTrackedCities_whenExecuteWeatherSync_thenFetchesAndSavesAllWeather() {
    settingsService.setTrackedCities("Krakow", "Warsaw", "Gdansk");
    WeatherModel krakowWeather = createWeatherModel("Krakow", 15.5);
    WeatherModel warsawWeather = createWeatherModel("Warsaw", 18.0);
    WeatherModel gdanskWeather = createWeatherModel("Gdansk", 12.0);
    when(weatherClient.getWeather("Krakow")).thenReturn(Optional.of(krakowWeather));
    when(weatherClient.getWeather("Warsaw")).thenReturn(Optional.of(warsawWeather));
    when(weatherClient.getWeather("Gdansk")).thenReturn(Optional.of(gdanskWeather));

    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isEqualTo(3);
    verify(weatherClient).getWeather("Krakow");
    verify(weatherClient).getWeather("Warsaw");
    verify(weatherClient).getWeather("Gdansk");
    assertThat(weatherRepository.count()).isEqualTo(3);
  }

  @Test
  void givenClientReturnsEmpty_whenExecuteWeatherSync_thenSkipsThatCity() {
    settingsService.setTrackedCities("Krakow", "Invalid");
    WeatherModel krakowWeather = createWeatherModel("Krakow", 15.5);
    when(weatherClient.getWeather("Krakow")).thenReturn(Optional.of(krakowWeather));
    when(weatherClient.getWeather("Invalid")).thenReturn(Optional.empty());

    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isEqualTo(1);
    verify(weatherClient).getWeather("Krakow");
    verify(weatherClient).getWeather("Invalid");
    assertThat(weatherRepository.count()).isEqualTo(1);
    assertThat(weatherRepository.findAll().get(0).getCity()).isEqualTo("Krakow");
  }

  @Test
  void givenAllCitiesReturnEmpty_whenExecuteWeatherSync_thenSavesNothing() {
    settingsService.setTrackedCities("Invalid1", "Invalid2");
    when(weatherClient.getWeather(any())).thenReturn(Optional.empty());

    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isZero();
    verify(weatherClient, times(2)).getWeather(any());
    assertThat(weatherRepository.count()).isZero();
  }

  @Test
  void givenTrackedCities_whenExecuteWeatherSync_thenCallsWeatherClientForEachCity() {
    List<String> cities = List.of("City1", "City2", "City3");
    settingsService.setTrackedCities(cities);
    when(weatherClient.getWeather(any())).thenReturn(Optional.empty());

    weatherProcessor.executeWeatherSync();

    for (String city : cities) {
      verify(weatherClient).getWeather(city);
    }
  }

  @Test
  void givenMixedResults_whenExecuteWeatherSync_thenOnlySavesSuccessfulFetches() {
    settingsService.setTrackedCities("Success1", "Fail", "Success2");
    WeatherModel success1 = createWeatherModel("Success1", 10.0);
    WeatherModel success2 = createWeatherModel("Success2", 20.0);
    when(weatherClient.getWeather("Success1")).thenReturn(Optional.of(success1));
    when(weatherClient.getWeather("Fail")).thenReturn(Optional.empty());
    when(weatherClient.getWeather("Success2")).thenReturn(Optional.of(success2));

    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isEqualTo(2);
    assertThat(weatherRepository.count()).isEqualTo(2);
    List<WeatherEntity> saved = weatherRepository.findAll();
    assertThat(saved)
        .extracting(WeatherEntity::getCity)
        .containsExactlyInAnyOrder("Success1", "Success2");
  }

  @Test
  void givenDuplicateCities_whenExecuteWeatherSync_thenDeduplicate() {
    settingsService.setTrackedCities("Krakow", "Krakow");
    WeatherModel weather = createWeatherModel("Krakow", 15.5);
    when(weatherClient.getWeather("Krakow")).thenReturn(Optional.of(weather));

    long result = weatherProcessor.executeWeatherSync();

    assertThat(result).isEqualTo(1);
    verify(weatherClient, times(1)).getWeather("Krakow");
    assertThat(weatherRepository.count()).isEqualTo(1);
  }

  private WeatherModel createWeatherModel(String city, Double temperature) {
    return new WeatherModel(
        city, temperature, "Test description", List.of("test"), OffsetDateTime.now(ZoneOffset.UTC));
  }
}
