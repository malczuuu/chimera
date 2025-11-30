package io.github.malczuuu.chimera.core.infrastructure.initialize;

import io.github.malczuuu.chimera.core.domain.parameter.SettingsService;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;

public class SettingsInitializer implements CommandLineRunner {

  private final SettingsService settingsService;
  private final String trackedCities;

  public SettingsInitializer(SettingsService settingsService, String trackedCities) {
    this.settingsService = settingsService;
    this.trackedCities = trackedCities;
  }

  @Transactional
  @Override
  public void run(String... args) {
    List<String> trackedCities = settingsService.findTrackedCities();

    if (trackedCities.isEmpty()) {
      settingsService.setTrackedCities(List.of(this.trackedCities.split(",")));
    }
  }
}
