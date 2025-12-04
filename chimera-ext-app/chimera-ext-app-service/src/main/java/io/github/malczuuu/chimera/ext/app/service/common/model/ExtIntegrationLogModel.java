package io.github.malczuuu.chimera.ext.app.service.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = ExtIntegrationLogModel.ExtIntegrationLogModelBuilder.class)
public class ExtIntegrationLogModel extends IntegrationLogModel {

  @JsonProperty("traceId")
  private final String traceId;

  public ExtIntegrationLogModel(
      String id,
      String label,
      String direction,
      String protocol,
      String method,
      String address,
      String requestAttributes,
      String requestBody,
      OffsetDateTime requestTimestamp,
      String responseBody,
      String responseStatus,
      String traceId) {
    super(
        id,
        label,
        direction,
        protocol,
        method,
        address,
        requestAttributes,
        requestBody,
        requestTimestamp,
        responseBody,
        responseStatus);
    this.traceId = traceId;
  }

  public static ExtIntegrationLogModelBuilder builder() {
    return new ExtIntegrationLogModelBuilder();
  }

  @JsonTypeInfo(
      use = JsonTypeInfo.Id.NAME,
      include = JsonTypeInfo.As.PROPERTY,
      property = "@type",
      defaultImpl = ExtIntegrationLogModel.class)
  @JsonSubTypes({
    @JsonSubTypes.Type(value = ExtIntegrationLogModel.class, name = "extImpl"),
  })
  public interface ExtIntegrationLogMixIn {}

  public static class ExtIntegrationLogModelBuilder extends IntegrationLogModelBuilder {

    private String traceId;

    @JsonSetter("traceId")
    public ExtIntegrationLogModelBuilder traceId(String traceId) {
      this.traceId = traceId;
      return this;
    }

    @Override
    public ExtIntegrationLogModel build() {
      return new ExtIntegrationLogModel(
          id,
          label,
          direction,
          protocol,
          method,
          address,
          requestAttributes,
          requestBody,
          requestTimestamp,
          responseBody,
          responseStatus,
          traceId);
    }
  }
}
