package io.github.malczuuu.chimera.core.domain.integration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class IntegrationDomainConfiguration {

  @ConditionalOnMissingBean(IntegrationLogFactory.class)
  @Bean
  public IntegrationLogFactory integrationLogFactory() {
    return new CoreIntegrationLogFactory();
  }

  @ConditionalOnMissingBean(IntegrationLogService.class)
  @Bean
  public IntegrationLogService integrationLogService(
      IntegrationLogFactory integrationLogFactory,
      IntegrationLogRepository integrationLogRepository) {
    return new CoreIntegrationLogService(integrationLogFactory, integrationLogRepository);
  }
}
