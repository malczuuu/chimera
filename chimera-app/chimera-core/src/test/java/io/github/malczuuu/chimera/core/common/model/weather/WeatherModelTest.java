package io.github.malczuuu.chimera.core.common.model.weather;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;

class WeatherModelTest {

  private final JsonMapper mapper =
      JsonMapper.builder()
          .findAndAddModules()
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
          .build();

  @Test
  void shouldDeserializeFromJson() throws Exception {
    String json =
        """
        {
          "city" : "Krakow",
          "temperature" : 15.5,
          "description" : "Partly cloudy",
          "labels" : [ "sunny", "warm" ],
          "timestamp" : "2025-12-04T10:00:00Z"
        }
        """;

    WeatherModel weather = mapper.readValue(json, WeatherModel.class);

    assertThat(weather.getCity()).isEqualTo("Krakow");
    assertThat(weather.getTemperature()).isEqualTo(15.5);
    assertThat(weather.getDescription()).isEqualTo("Partly cloudy");
    assertThat(weather.getLabels()).containsExactly("sunny", "warm");
    assertThat(weather.getTimestamp())
        .isEqualTo(Instant.parse("2025-12-04T10:00:00Z").atOffset(ZoneOffset.UTC));
  }

  @Test
  void shouldSerializeToJson() throws Exception {
    WeatherModel weather =
        WeatherModel.builder()
            .city("Warsaw")
            .temperature(18.0)
            .description("Clear sky")
            .labels(List.of("clear", "hot"))
            .timestamp(OffsetDateTime.of(2025, 12, 4, 15, 30, 0, 0, ZoneOffset.UTC))
            .build();

    String json = mapper.writeValueAsString(weather);

    String expectedJson =
        """
        {
          "city" : "Warsaw",
          "temperature" : 18.0,
          "description" : "Clear sky",
          "labels" : [ "clear", "hot" ],
          "timestamp" : "2025-12-04T15:30:00Z"
        }
        """;
    assertThat(mapper.readTree(json)).isEqualTo(mapper.readTree(expectedJson));
  }
}
