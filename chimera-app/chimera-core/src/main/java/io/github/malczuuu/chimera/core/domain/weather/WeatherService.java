package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.common.model.Content;
import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import java.util.List;

public interface WeatherService {

  Content<WeatherModel> getWeather(String city);

  void saveWeather(WeatherModel weatherModel);

  void saveWeathers(List<WeatherModel> weathers);
}
