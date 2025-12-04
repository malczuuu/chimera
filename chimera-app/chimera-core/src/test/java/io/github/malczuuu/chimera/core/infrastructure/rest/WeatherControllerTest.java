package io.github.malczuuu.chimera.core.infrastructure.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.malczuuu.chimera.core.CoreTestApplication;
import io.github.malczuuu.chimera.core.domain.weather.WeatherEntity;
import io.github.malczuuu.chimera.core.domain.weather.WeatherRepository;
import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {CoreTestApplication.class})
@AutoConfigureMockMvc
@PostgresContainerTest
class WeatherControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private WeatherRepository weatherRepository;

  @BeforeEach
  void beforeEach() {
    weatherRepository.deleteAll();
  }

  @Test
  void givenNoWeatherData_whenGetWeather_thenReturnsEmptyContent() throws Exception {
    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(0)));
  }

  @Test
  void givenWeatherDataForCity_whenGetWeatherForThatCity_thenReturnsWeatherData() throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].city", is("Krakow")))
        .andExpect(jsonPath("$.content[0].temperature", is(15.5)))
        .andExpect(jsonPath("$.content[0].description", is("Cloudy")))
        .andExpect(jsonPath("$.content[0].timestamp", notNullValue()));
  }

  @Test
  void givenWeatherDataForMultipleCities_whenGetWeatherForOneCity_thenReturnsOnlyThatCityData()
      throws Exception {
    WeatherEntity krakowWeather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    WeatherEntity warsawWeather = createWeatherEntity("Warsaw", 18.0, "Sunny");
    weatherRepository.save(krakowWeather);
    weatherRepository.save(warsawWeather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].city", is("Krakow")));
  }

  @Test
  void givenMultipleWeatherEntriesForCity_whenGetWeather_thenReturnsLimitedEntries()
      throws Exception {
    for (int i = 0; i < 10; i++) {
      WeatherEntity weather = createWeatherEntity("Krakow", 15.0 + i, "Description " + i);
      weather.setTimestamp(Instant.now().minusSeconds(i * 60));
      weatherRepository.save(weather);
    }

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(10)));
  }

  @Test
  void givenMultipleWeatherEntriesForCity_whenGetWeather_thenReturnsOrderedByTimestampDesc()
      throws Exception {
    WeatherEntity older = createWeatherEntity("Krakow", 10.0, "Older");
    older.setTimestamp(Instant.now().minusSeconds(3600));
    WeatherEntity newer = createWeatherEntity("Krakow", 20.0, "Newer");
    newer.setTimestamp(Instant.now().minusSeconds(1800));
    WeatherEntity newest = createWeatherEntity("Krakow", 25.0, "Newest");
    newest.setTimestamp(Instant.now());

    weatherRepository.save(older);
    weatherRepository.save(newer);
    weatherRepository.save(newest);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(3)))
        .andExpect(jsonPath("$.content[0].temperature", is(25.0)))
        .andExpect(jsonPath("$.content[1].temperature", is(20.0)))
        .andExpect(jsonPath("$.content[2].temperature", is(10.0)));
  }

  @Test
  void givenWeatherDataWithLabels_whenGetWeather_thenReturnsLabelsAsArray() throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    weather.setLabels("label1,label2,label3");
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].labels", hasSize(3)))
        .andExpect(jsonPath("$.content[0].labels[0]", is("label1")))
        .andExpect(jsonPath("$.content[0].labels[1]", is("label2")))
        .andExpect(jsonPath("$.content[0].labels[2]", is("label3")));
  }

  @Test
  void givenWeatherDataWithNullLabels_whenGetWeather_thenReturnsEmptyLabelsArray()
      throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    weather.setLabels(null);
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].labels", hasSize(0)));
  }

  @Test
  void givenWeatherDataWithEmptyLabels_whenGetWeather_thenReturnsEmptyLabelsArray()
      throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    weather.setLabels("");
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].labels", hasSize(0)));
  }

  @Test
  void givenMissingCityParameter_whenGetWeather_thenReturnsBadRequest() throws Exception {
    mockMvc
        .perform(get("/api/weather").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenCaseSensitiveCity_whenGetWeatherWithDifferentCase_thenReturnsEmpty() throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "KRAKOW").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(0)));
  }

  @Test
  void givenNegativeTemperature_whenGetWeather_thenReturnsCorrectNegativeValue() throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", -5.5, "Freezing");
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].temperature", is(-5.5)))
        .andExpect(jsonPath("$.content[0].description", is("Freezing")));
  }

  @Test
  void givenWeatherWithNullTemperature_whenGetWeather_thenReturnsNullTemperature()
      throws Exception {
    WeatherEntity weather = createWeatherEntity("Krakow", null, "Unknown");
    weatherRepository.save(weather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].city", is("Krakow")))
        .andExpect(jsonPath("$.content[0].description", is("Unknown")));
  }

  @Test
  void givenWeatherForDifferentCities_whenGetWeatherForEachCity_thenReturnsCorrectData()
      throws Exception {
    WeatherEntity krakowWeather = createWeatherEntity("Krakow", 15.5, "Cloudy");
    WeatherEntity warsawWeather = createWeatherEntity("Warsaw", 18.0, "Sunny");
    WeatherEntity gdanskWeather = createWeatherEntity("Gdansk", 12.0, "Rainy");
    weatherRepository.save(krakowWeather);
    weatherRepository.save(warsawWeather);
    weatherRepository.save(gdanskWeather);

    mockMvc
        .perform(get("/api/weather").param("city", "Krakow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].city", is("Krakow")));

    mockMvc
        .perform(get("/api/weather").param("city", "Warsaw").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].city", is("Warsaw")));

    mockMvc
        .perform(get("/api/weather").param("city", "Gdansk").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andExpect(jsonPath("$.content[0].city", is("Gdansk")));
  }

  private WeatherEntity createWeatherEntity(String city, Double temperature, String description) {
    WeatherEntity entity = new WeatherEntity();
    entity.setCity(city);
    entity.setTemperature(temperature);
    entity.setDescription(description);
    entity.setLabels("");
    entity.setTimestamp(Instant.now());
    return entity;
  }
}
