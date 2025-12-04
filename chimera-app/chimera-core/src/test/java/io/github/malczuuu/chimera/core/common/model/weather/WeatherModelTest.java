package io.github.malczuuu.chimera.core.common.model.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;

class WeatherModelTest {

  private final JsonMapper mapper =
      JsonMapper.builder()
          .findAndAddModules()
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
          .configure(SerializationFeature.INDENT_OUTPUT, true)
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

    assertEquals("Krakow", weather.getCity());
    assertEquals(15.5, weather.getTemperature());
    assertEquals("Partly cloudy", weather.getDescription());
    assertEquals(List.of("sunny", "warm"), weather.getLabels());
    assertEquals(
        OffsetDateTime.of(2025, 12, 4, 10, 0, 0, 0, ZoneOffset.UTC), weather.getTimestamp());
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
        }""";
    assertThat(json).isEqualTo(expectedJson);
  }
}
