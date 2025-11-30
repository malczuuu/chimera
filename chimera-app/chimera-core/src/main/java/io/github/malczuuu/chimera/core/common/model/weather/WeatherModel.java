package io.github.malczuuu.chimera.core.common.model.weather;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class WeatherModel {

  @JsonProperty("city")
  private final String city;

  @JsonProperty("temperature")
  private final Double temperature;

  @JsonProperty("description")
  private final String description;

  @JsonProperty("labels")
  private final List<String> labels;

  @JsonProperty("timestamp")
  private final OffsetDateTime timestamp;
}
