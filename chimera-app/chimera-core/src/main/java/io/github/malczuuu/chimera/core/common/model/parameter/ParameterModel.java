package io.github.malczuuu.chimera.core.common.model.parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = ParameterModel.ParameterModelBuilder.class)
public class ParameterModel {

  @JsonProperty("id")
  private final String id;

  @JsonProperty("code")
  private final String code;

  @JsonProperty("value")
  private final String value;

  public ParameterModel(String id, String code, String value) {
    this.id = id;
    this.code = code;
    this.value = value;
  }

  public static ParameterModelBuilder builder() {
    return new ParameterModelBuilder();
  }

  public static class ParameterModelBuilder {

    private String id;
    private String code;
    private String value;

    public ParameterModelBuilder() {}

    @JsonSetter("id")
    public ParameterModelBuilder id(String id) {
      this.id = id;
      return this;
    }

    @JsonSetter("code")
    public ParameterModelBuilder code(String code) {
      this.code = code;
      return this;
    }

    @JsonSetter("value")
    public ParameterModelBuilder value(String value) {
      this.value = value;
      return this;
    }

    public ParameterModel build() {
      return new ParameterModel(id, code, value);
    }
  }
}
