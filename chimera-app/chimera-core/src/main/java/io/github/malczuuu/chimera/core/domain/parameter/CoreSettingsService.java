package io.github.malczuuu.chimera.core.domain.parameter;

import java.util.Collection;
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
        .distinct()
        .sorted()
        .toList();
  }

  @Override
  public void setTrackedCities(Collection<String> trackedCities) {
    trackedCities = trackedCities.stream().distinct().sorted().toList();
    parameterService.setParameter(ParameterNames.TRACKED_CITIES, String.join(",", trackedCities));
  }
}
