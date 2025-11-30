package io.github.malczuuu.chimera.core.domain.integration;

import io.github.malczuuu.chimera.core.common.Discriminator;
import io.github.malczuuu.chimera.core.infrastructure.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "integration_logs")
@SequenceGenerator(
    name = "seq_integration_logs",
    sequenceName = "seq_integration_logs",
    allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", length = 1)
@DiscriminatorValue(Discriminator.CORE)
public class IntegrationLogEntity extends AbstractEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_integration_logs")
  private Long id;

  @Column(name = "label", length = 64)
  private String label;

  @Column(name = "direction", length = 64)
  private String direction;

  @Column(name = "protocol", length = 64)
  private String protocol;

  @Column(name = "method", length = 64)
  private String method;

  @Column(name = "address", length = 2048)
  private String address;

  @Column(name = "request_attributes", length = 2048)
  private String requestAttributes;

  @Column(name = "request_body", length = 2048)
  private String requestBody;

  @Column(name = "request_timestamp")
  private Instant requestTimestamp;

  @Column(name = "response_body", length = 2048)
  private String responseBody;

  @Column(name = "response_status", length = 256)
  private String responseStatus;
}
