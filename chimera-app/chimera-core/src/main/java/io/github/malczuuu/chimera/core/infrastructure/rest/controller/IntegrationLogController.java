package io.github.malczuuu.chimera.core.infrastructure.rest.controller;

import io.github.malczuuu.chimera.core.common.model.Content;
import io.github.malczuuu.chimera.core.common.model.IdentityModel;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/integration-logs")
public class IntegrationLogController {

  private final IntegrationLogService integrationLogService;

  public IntegrationLogController(IntegrationLogService integrationLogService) {
    this.integrationLogService = integrationLogService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Content<IntegrationLogModel>> getIntegrationLogs(
      HttpServletRequest request) {
    Content<IntegrationLogModel> entities = integrationLogService.getIntegrationLogs();
    return ResponseEntity.ok(entities);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IntegrationLogModel> getIntegrationLogById(
      @PathVariable("id") String id, HttpServletRequest request) {
    IntegrationLogModel result = integrationLogService.getIntegrationLog(id);
    return ResponseEntity.ok(result);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> createIntegrationLog(
      @RequestBody IntegrationLogModel requestBody, HttpServletRequest request) {
    IdentityModel identity = integrationLogService.createIntegrationLog(requestBody);
    return ResponseEntity.created(URI.create("/api/integration-logs/" + identity.getId())).build();
  }
}
