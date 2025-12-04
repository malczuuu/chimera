package io.github.malczuuu.chimera.core.domain.integration;

import io.github.malczuuu.chimera.core.common.exception.ResourceNotFoundException;
import io.github.malczuuu.chimera.core.common.model.Content;
import io.github.malczuuu.chimera.core.common.model.IdentityModel;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import java.time.ZoneOffset;
import java.util.List;

public class CoreIntegrationLogService implements IntegrationLogService {

  private final IntegrationLogFactory integrationLogFactory;
  private final IntegrationLogRepository integrationLogRepository;

  public CoreIntegrationLogService(
      IntegrationLogFactory integrationLogFactory,
      IntegrationLogRepository integrationLogRepository) {
    this.integrationLogFactory = integrationLogFactory;
    this.integrationLogRepository = integrationLogRepository;
  }

  @Override
  public Content<IntegrationLogModel> getIntegrationLogs() {
    List<IntegrationLogEntity> entities =
        integrationLogRepository.findAllByOrderByRequestTimestampDesc();
    return new Content<>(entities.stream().map(this::toIntegrationLogModel).toList());
  }

  @Override
  public IntegrationLogModel getIntegrationLog(String id) {
    return integrationLogRepository
        .findById(parseId(id))
        .map(this::toIntegrationLogModel)
        .orElseThrow(() -> new ResourceNotFoundException("integration-log", id));
  }

  private long parseId(String id) {
    try {
      return Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new ResourceNotFoundException("integration-log", id);
    }
  }

  @Override
  public IdentityModel createIntegrationLog(IntegrationLogModel integrationLog) {
    IntegrationLogEntity entity = createIntegrationLogEntity(integrationLog);
    entity = integrationLogRepository.save(entity);
    return new IdentityModel(entity.getIdAsString());
  }

  protected IntegrationLogEntity createIntegrationLogEntity(IntegrationLogModel integrationLog) {
    IntegrationLogEntity entity = integrationLogFactory.createEntity();
    entity.setLabel(integrationLog.getLabel());
    entity.setDirection(integrationLog.getDirection());
    entity.setProtocol(integrationLog.getProtocol());
    entity.setMethod(integrationLog.getMethod());
    entity.setAddress(integrationLog.getAddress());
    entity.setRequestAttributes(integrationLog.getRequestAttributes());
    entity.setRequestBody(integrationLog.getRequestBody());
    entity.setRequestTimestamp(
        integrationLog.getRequestTimestamp() != null
            ? integrationLog.getRequestTimestamp().toInstant()
            : null);
    entity.setResponseBody(integrationLog.getResponseBody());
    entity.setResponseStatus(integrationLog.getResponseStatus());
    return entity;
  }

  protected IntegrationLogModel toIntegrationLogModel(IntegrationLogEntity entity) {
    return new IntegrationLogModel(
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
        entity.getResponseStatus());
  }
}
