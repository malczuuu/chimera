package io.github.malczuuu.chimera.core.domain.integration;

public class CoreIntegrationLogFactory implements IntegrationLogFactory {

  @Override
  public IntegrationLogEntity createEntity() {
    return new IntegrationLogEntity();
  }
}
