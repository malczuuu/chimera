package io.github.malczuuu.chimera.ext.app.service.infrastructure;

import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.ext.app.service.common.model.ExtIntegrationLogModel;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ObjectMapperConfiguration {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer extMixInCustomizer() {
    return builder -> {
      builder.mixIn(IntegrationLogModel.class, ExtIntegrationLogModel.ExtIntegrationLogMixIn.class);
    };
  }
}
