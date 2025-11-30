package io.github.malczuuu.chimera.core.domain;

import io.github.malczuuu.chimera.core.domain.integration.IntegrationDomainConfiguration;
import io.github.malczuuu.chimera.core.domain.parameter.ParameterDomainConfiguration;
import io.github.malczuuu.chimera.core.domain.weather.WeatherDomainConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({
  IntegrationDomainConfiguration.class,
  ParameterDomainConfiguration.class,
  WeatherDomainConfiguration.class
})
public class ChimeraDomainConfiguration {}
