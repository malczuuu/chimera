package io.github.malczuuu.chimera.core.domain.weather;

public class CoreWeatherFactory implements WeatherFactory {

  @Override
  public WeatherEntity createEntity() {
    return new WeatherEntity();
  }
}
