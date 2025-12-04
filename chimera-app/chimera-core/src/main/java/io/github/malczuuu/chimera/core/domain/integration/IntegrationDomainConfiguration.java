package io.github.malczuuu.chimera.core.domain.integration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

@Configuration(proxyBeanMethods = false)
public class IntegrationDomainConfiguration {

  @ConditionalOnMissingBean(IntegrationLogFactory.class)
  @Bean
  public IntegrationLogFactory integrationLogFactory() {
    return new CoreIntegrationLogFactory();
  }

  @ConditionalOnMissingBean(IntegrationLogRepository.class)
  @Bean
  public JpaRepositoryFactoryBean<IntegrationLogRepository, IntegrationLogEntity, Long>
      integrationLogRepository() {
    return new JpaRepositoryFactoryBean<>(IntegrationLogRepository.class);
  }

  @ConditionalOnMissingBean(IntegrationLogService.class)
  @Bean
  public IntegrationLogService integrationLogService(
      IntegrationLogFactory integrationLogFactory,
      IntegrationLogRepository integrationLogRepository) {
    return new CoreIntegrationLogService(integrationLogFactory, integrationLogRepository);
  }
}
