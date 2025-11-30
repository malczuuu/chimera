package io.github.malczuuu.chimera.core.common.model.integration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC, onConstructor_ = @JsonCreator)
public class IntegrationLogModel {

  @JsonProperty("id")
  private final String id;

  @JsonProperty("label")
  private final String label;

  @JsonProperty("direction")
  private final String direction;

  @JsonProperty("protocol")
  private final String protocol;

  @JsonProperty("method")
  private final String method;

  @JsonProperty("address")
  private final String address;

  @JsonProperty("requestAttributes")
  private final String requestAttributes;

  @JsonProperty("requestBody")
  private final String requestBody;

  @JsonProperty("requestTimestamp")
  private final OffsetDateTime requestTimestamp;

  @JsonProperty("responseBody")
  private final String responseBody;

  @JsonProperty("responseStatus")
  private final String responseStatus;
}
