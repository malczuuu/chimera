package io.github.malczuuu.chimera.ext.app.service.domain.integration;

import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("X")
public class ExtIntegrationLogEntity extends IntegrationLogEntity {

  @Column(name = "trace_id", length = 256)
  private String traceId;
}
