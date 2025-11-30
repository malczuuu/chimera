package io.github.malczuuu.chimera.core.infrastructure;

import io.github.malczuuu.chimera.core.infrastructure.initialize.InitializeInfraConfiguration;
import io.github.malczuuu.chimera.core.infrastructure.openweathermap.OpenWeatherMapInfraConfiguration;
import io.github.malczuuu.chimera.core.infrastructure.rest.RestInfraConfiguration;
import io.github.malczuuu.chimera.core.infrastructure.scheduler.SchedulerInfraConfiguration;
import io.github.malczuuu.chimera.core.infrastructure.time.TimeInfraConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({
  InitializeInfraConfiguration.class,
  OpenWeatherMapInfraConfiguration.class,
  RestInfraConfiguration.class,
  SchedulerInfraConfiguration.class,
  TimeInfraConfiguration.class,
})
public class ChimeraInfraConfiguration {}
