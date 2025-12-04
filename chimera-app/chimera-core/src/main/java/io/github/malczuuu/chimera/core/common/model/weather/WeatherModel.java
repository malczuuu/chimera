package io.github.malczuuu.chimera.core.common.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = WeatherModel.WeatherModelBuilder.class)
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

  public WeatherModel(
      String city,
      Double temperature,
      String description,
      List<String> labels,
      OffsetDateTime timestamp) {
    this.city = city;
    this.temperature = temperature;
    this.description = description;
    this.labels = labels;
    this.timestamp = timestamp;
  }

  public static WeatherModelBuilder builder() {
    return new WeatherModelBuilder();
  }

  public static class WeatherModelBuilder {

    private String city;
    private Double temperature;
    private String description;
    private List<String> labels;
    private OffsetDateTime timestamp;

    public WeatherModelBuilder() {}

    @JsonSetter("city")
    public WeatherModelBuilder city(String city) {
      this.city = city;
      return this;
    }

    @JsonSetter("temperature")
    public WeatherModelBuilder temperature(Double temperature) {
      this.temperature = temperature;
      return this;
    }

    @JsonSetter("description")
    public WeatherModelBuilder description(String description) {
      this.description = description;
      return this;
    }

    @JsonSetter("labels")
    public WeatherModelBuilder labels(List<String> labels) {
      this.labels = labels;
      return this;
    }

    @JsonSetter("timestamp")
    public WeatherModelBuilder timestamp(OffsetDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public WeatherModel build() {
      return new WeatherModel(city, temperature, description, labels, timestamp);
    }
  }
}
