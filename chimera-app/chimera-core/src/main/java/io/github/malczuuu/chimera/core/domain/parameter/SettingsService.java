package io.github.malczuuu.chimera.core.domain.parameter;

import java.util.List;

public interface SettingsService {

  List<String> findTrackedCities();

  void setTrackedCities(List<String> trackedCities);
}
