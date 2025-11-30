package io.github.malczuuu.chimera.core.domain.integration;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration(proxyBeanMethods = false)
public class IntegrationDomainConfiguration {

  @ConditionalOnMissingBean(IntegrationLogFactory.class)
  @Bean
  public IntegrationLogFactory integrationLogFactory() {
    return new CoreIntegrationLogFactory();
  }

  @ConditionalOnMissingBean(IntegrationLogRepository.class)
  @Bean
  public IntegrationLogRepository integrationLogRepository(EntityManager entityManager) {
    return new JpaRepositoryFactory(entityManager).getRepository(IntegrationLogRepository.class);
  }

  @ConditionalOnMissingBean(IntegrationLogService.class)
  @Bean
  public IntegrationLogService integrationLogService(
      IntegrationLogFactory integrationLogFactory,
      IntegrationLogRepository integrationLogRepository) {
    return new CoreIntegrationLogService(integrationLogFactory, integrationLogRepository);
  }
}
