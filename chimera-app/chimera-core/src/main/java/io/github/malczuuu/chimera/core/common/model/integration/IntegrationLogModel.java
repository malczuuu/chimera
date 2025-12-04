package io.github.malczuuu.chimera.core.common.model.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = IntegrationLogModel.IntegrationLogModelBuilder.class)
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

  public IntegrationLogModel(
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
      String responseStatus) {
    this.id = id;
    this.label = label;
    this.direction = direction;
    this.protocol = protocol;
    this.method = method;
    this.address = address;
    this.requestAttributes = requestAttributes;
    this.requestBody = requestBody;
    this.requestTimestamp = requestTimestamp;
    this.responseBody = responseBody;
    this.responseStatus = responseStatus;
  }

  public static IntegrationLogModelBuilder builder() {
    return new IntegrationLogModelBuilder();
  }

  public static class IntegrationLogModelBuilder {

    protected String id;
    protected String label;
    protected String direction;
    protected String protocol;
    protected String method;
    protected String address;
    protected String requestAttributes;
    protected String requestBody;
    protected OffsetDateTime requestTimestamp;
    protected String responseBody;
    protected String responseStatus;

    public IntegrationLogModelBuilder() {}

    @JsonSetter("id")
    public IntegrationLogModelBuilder id(String id) {
      this.id = id;
      return this;
    }

    @JsonSetter("label")
    public IntegrationLogModelBuilder label(String label) {
      this.label = label;
      return this;
    }

    @JsonSetter("direction")
    public IntegrationLogModelBuilder direction(String direction) {
      this.direction = direction;
      return this;
    }

    @JsonSetter("protocol")
    public IntegrationLogModelBuilder protocol(String protocol) {
      this.protocol = protocol;
      return this;
    }

    @JsonSetter("method")
    public IntegrationLogModelBuilder method(String method) {
      this.method = method;
      return this;
    }

    @JsonSetter("address")
    public IntegrationLogModelBuilder address(String address) {
      this.address = address;
      return this;
    }

    @JsonSetter("requestAttributes")
    public IntegrationLogModelBuilder requestAttributes(String requestAttributes) {
      this.requestAttributes = requestAttributes;
      return this;
    }

    @JsonSetter("requestBody")
    public IntegrationLogModelBuilder requestBody(String requestBody) {
      this.requestBody = requestBody;
      return this;
    }

    @JsonSetter("requestTimestamp")
    public IntegrationLogModelBuilder requestTimestamp(OffsetDateTime requestTimestamp) {
      this.requestTimestamp = requestTimestamp;
      return this;
    }

    @JsonSetter("responseBody")
    public IntegrationLogModelBuilder responseBody(String responseBody) {
      this.responseBody = responseBody;
      return this;
    }

    @JsonSetter("responseStatus")
    public IntegrationLogModelBuilder responseStatus(String responseStatus) {
      this.responseStatus = responseStatus;
      return this;
    }

    public IntegrationLogModel build() {
      return new IntegrationLogModel(
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
    }
  }
}
