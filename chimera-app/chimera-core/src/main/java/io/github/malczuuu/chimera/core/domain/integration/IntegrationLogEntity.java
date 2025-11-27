package io.github.malczuuu.chimera.core.domain.integration;

import io.github.malczuuu.chimera.core.infrastructure.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(
    name = "integration_logs",
    indexes = {
      @Index(
          name = "idx_integration_logs_label_request_timestamp",
          columnList = "label, request_timestamp")
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class IntegrationLogEntity extends AbstractEntity {

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

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRequestAttributes() {
    return requestAttributes;
  }

  public void setRequestAttributes(String requestAttributes) {
    this.requestAttributes = requestAttributes;
  }

  public String getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(String requestBody) {
    this.requestBody = requestBody;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public Instant getRequestTimestamp() {
    return requestTimestamp;
  }

  public void setRequestTimestamp(Instant requestTimestamp) {
    this.requestTimestamp = requestTimestamp;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public String getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(String responseStatus) {
    this.responseStatus = responseStatus;
  }
}
