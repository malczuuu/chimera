package io.github.malczuuu.chimera.core.domain.parameter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface SettingsService {

  List<String> findTrackedCities();

  void setTrackedCities(Collection<String> trackedCities);

  default void setTrackedCities(String... trackedCities) {
    setTrackedCities(Arrays.asList(trackedCities));
  }
}
