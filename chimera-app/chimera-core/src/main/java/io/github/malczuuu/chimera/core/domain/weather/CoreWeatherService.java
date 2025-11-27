package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.common.model.content.Content;
import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Limit;
import org.springframework.transaction.annotation.Transactional;

public class CoreWeatherService implements WeatherService {

  private final WeatherFactory weatherFactory;
  private final WeatherRepository weatherRepository;

  public CoreWeatherService(WeatherFactory weatherFactory, WeatherRepository weatherRepository) {
    this.weatherFactory = weatherFactory;
    this.weatherRepository = weatherRepository;
  }

  @Override
  public Content<WeatherModel> getWeather(String city) {
    List<WeatherEntity> entities =
        weatherRepository.findAllByCityOrderByTimestampDesc(city, Limit.of(50));
    List<WeatherModel> weather = entities.stream().map(this::toWeatherModel).toList();
    return new Content<>(weather);
  }

  @Override
  public void saveWeather(WeatherModel weatherModel) {
    WeatherEntity entity = toWeatherEntity(weatherModel);
    weatherRepository.save(entity);
  }

  @Transactional
  @Override
  public void saveWeathers(List<WeatherModel> weathers) {
    List<WeatherEntity> entities = weathers.stream().map(this::toWeatherEntity).toList();
    weatherRepository.saveAll(entities);
  }

  private WeatherModel toWeatherModel(WeatherEntity entity) {
    return new WeatherModel(
        entity.getCity(),
        entity.getTemperature(),
        entity.getDescription(),
        Arrays.asList(entity.getLabels().split(",")),
        entity.getTimestamp().atOffset(ZoneOffset.UTC));
  }

  private WeatherEntity toWeatherEntity(WeatherModel weatherModel) {
    WeatherEntity entity = weatherFactory.createEntity();
    entity.setCity(weatherModel.getCity());
    entity.setTemperature(weatherModel.getTemperature());
    entity.setDescription(weatherModel.getDescription());
    entity.setLabels(String.join(",", weatherModel.getLabels()));
    entity.setTimestamp(weatherModel.getTimestamp().toInstant());
    return entity;
  }
}
