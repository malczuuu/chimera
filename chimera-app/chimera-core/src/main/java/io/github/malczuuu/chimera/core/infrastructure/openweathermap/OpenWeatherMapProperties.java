package io.github.malczuuu.chimera.core.infrastructure.openweathermap;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@ConfigurationProperties(prefix = "chimera.openweathermap")
public class OpenWeatherMapProperties {

  private final String weatherEndpoint;
  private final String token;

  public OpenWeatherMapProperties(
      @DefaultValue("http://localhost:1080/data/2.5/weather") String weatherEndpoint,
      @DefaultValue("") String token) {
    this.weatherEndpoint = weatherEndpoint;
    this.token = token;
  }
}
