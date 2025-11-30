package io.github.malczuuu.chimera.ext.app.service.domain.integration;

import io.github.malczuuu.chimera.core.domain.integration.CoreIntegrationLogFactory;
import org.springframework.stereotype.Component;

@Component
public class ExtIntegrationLogFactory extends CoreIntegrationLogFactory {

  @Override
  public ExtIntegrationLogEntity createEntity() {
    return new ExtIntegrationLogEntity();
  }
}
