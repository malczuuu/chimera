package io.github.malczuuu.chimera.core.infrastructure.time;

import java.time.Clock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TimeInfraConfiguration {

  @ConditionalOnMissingBean(Clock.class)
  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
