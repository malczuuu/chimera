package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import java.util.Optional;

public interface WeatherClient {

  Optional<WeatherModel> getWeather(String city);
}
