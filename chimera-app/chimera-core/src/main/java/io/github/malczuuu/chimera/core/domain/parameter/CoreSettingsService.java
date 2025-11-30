package io.github.malczuuu.chimera.core.domain.parameter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class CoreSettingsService implements SettingsService {

  private final ParameterService parameterService;

  public CoreSettingsService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  @Override
  public List<String> findTrackedCities() {
    return parameterService.findParameter(ParameterNames.TRACKED_CITIES).stream()
        .filter(parameter -> Objects.nonNull(parameter.getValue()))
        .flatMap(parameter -> Stream.of(parameter.getValue().split(",")))
        .map(String::trim)
        .toList();
  }

  @Override
  public void setTrackedCities(List<String> trackedCities) {
    parameterService.setParameter(ParameterNames.TRACKED_CITIES, String.join(",", trackedCities));
  }
}
