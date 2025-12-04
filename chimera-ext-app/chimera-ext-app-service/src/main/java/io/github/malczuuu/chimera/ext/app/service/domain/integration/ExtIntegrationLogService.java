package io.github.malczuuu.chimera.ext.app.service.domain.integration;

import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.core.domain.integration.CoreIntegrationLogService;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogEntity;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogFactory;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogRepository;
import io.github.malczuuu.chimera.ext.app.service.common.model.ExtIntegrationLogModel;
import java.time.ZoneOffset;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ExtIntegrationLogService extends CoreIntegrationLogService {

  public ExtIntegrationLogService(
      IntegrationLogFactory integrationLogFactory,
      IntegrationLogRepository integrationLogRepository) {
    super(integrationLogFactory, integrationLogRepository);
  }

  @Override
  protected IntegrationLogEntity createIntegrationLogEntity(IntegrationLogModel integrationLog) {
    ExtIntegrationLogEntity entity =
        (ExtIntegrationLogEntity) super.createIntegrationLogEntity(integrationLog);

    entity.setTraceId(
        integrationLog instanceof ExtIntegrationLogModel extModel
            ? extModel.getTraceId()
            : UUID.randomUUID().toString());

    return entity;
  }

  @Override
  protected ExtIntegrationLogModel toIntegrationLogModel(IntegrationLogEntity entity) {
    return new ExtIntegrationLogModel(
        entity.getIdAsString(),
        entity.getLabel(),
        entity.getDirection(),
        entity.getProtocol(),
        entity.getMethod(),
        entity.getAddress(),
        entity.getRequestAttributes(),
        entity.getRequestBody(),
        entity.getRequestTimestamp().atOffset(ZoneOffset.UTC),
        entity.getResponseBody(),
        entity.getResponseStatus(),
        entity instanceof ExtIntegrationLogEntity extEntity ? extEntity.getTraceId() : null);
  }
}
