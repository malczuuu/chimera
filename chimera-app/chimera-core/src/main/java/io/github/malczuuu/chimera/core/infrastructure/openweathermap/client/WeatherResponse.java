package io.github.malczuuu.chimera.core.infrastructure.openweathermap.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record WeatherResponse(
    @JsonProperty("coord") Coord coord,
    @JsonProperty("weather") List<Weather> weather,
    @JsonProperty("base") String base,
    @JsonProperty("main") Main main,
    @JsonProperty("visibility") Integer visibility,
    @JsonProperty("wind") Wind wind,
    @JsonProperty("clouds") Clouds clouds,
    @JsonProperty("dt") Long dt,
    @JsonProperty("sys") Sys sys,
    @JsonProperty("timezone") Integer timezone,
    @JsonProperty("id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("cod") Integer cod) {

  public record Wind(
      @JsonProperty("speed") Double speed,
      @JsonProperty("deg") Integer deg,
      @JsonProperty("gust") Double gust) {}

  public record Weather(
      @JsonProperty("id") Long id,
      @JsonProperty("main") String main,
      @JsonProperty("description") String description,
      @JsonProperty("icon") String icon) {}

  public record Sys(
      @JsonProperty("type") Integer type,
      @JsonProperty("id") Long id,
      @JsonProperty("country") String country,
      @JsonProperty("sunrise") Long sunrise,
      @JsonProperty("sunset") Long sunset) {}

  public record Main(
      @JsonProperty("temp") Double temp,
      @JsonProperty("feels_like") Double feelsLike,
      @JsonProperty("temp_min") Double tempMin,
      @JsonProperty("temp_max") Double tempMax,
      @JsonProperty("pressure") Integer pressure,
      @JsonProperty("humidity") Integer humidity,
      @JsonProperty("sea_level") Integer seaLevel,
      @JsonProperty("grnd_level") Integer grndLevel) {}

  public record Coord(@JsonProperty("lon") Double lon, @JsonProperty("lat") Double lat) {}

  public record Clouds(@JsonProperty("all") Integer all) {}
}
