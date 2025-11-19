package io.github.malczuuu.chimera.core.infrastructure.openweathermap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "chimera.openweathermap")
public class OpenWeatherMapProperties {

  private final String weatherEndpoint;
  private final String token;

  public OpenWeatherMapProperties(
      @DefaultValue("https://api.openweathermap.org/data/2.5/weather") String weatherEndpoint,
      @DefaultValue("") String token) {
    this.weatherEndpoint = weatherEndpoint;
    this.token = token;
  }

  public String getWeatherEndpoint() {
    return weatherEndpoint;
  }

  public String getToken() {
    return token;
  }
}
