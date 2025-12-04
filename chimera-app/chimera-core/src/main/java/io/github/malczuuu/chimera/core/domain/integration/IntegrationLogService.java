package io.github.malczuuu.chimera.core.domain.integration;

import io.github.malczuuu.chimera.core.common.model.Content;
import io.github.malczuuu.chimera.core.common.model.IdentityModel;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;

public interface IntegrationLogService {

  Content<IntegrationLogModel> getIntegrationLogs();

  IntegrationLogModel getIntegrationLog(String id);

  IdentityModel createIntegrationLog(IntegrationLogModel integrationLog);
}
