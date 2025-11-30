package io.github.malczuuu.chimera.ext.app.service.domain.integration;

import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.core.domain.integration.CoreIntegrationLogService;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogEntity;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogFactory;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ExtIntegrationLogService extends CoreIntegrationLogService {

  public ExtIntegrationLogService(
      IntegrationLogFactory integrationLogFactory,
      IntegrationLogRepository integrationLogRepository) {
    super(integrationLogFactory, integrationLogRepository);
  }

  // example of assigning extended field
  @Override
  protected IntegrationLogEntity createIntegrationLogEntity(IntegrationLogModel integrationLog) {
    ExtIntegrationLogEntity entity =
        (ExtIntegrationLogEntity) super.createIntegrationLogEntity(integrationLog);

    entity.setTraceId(UUID.randomUUID().toString());
    return entity;
  }
}
