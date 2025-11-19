package io.github.malczuuu.chimera.core.common.model.parameter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class ParameterModel {

  @JsonProperty("id")
  private final String id;

  @JsonProperty("code")
  private final String code;

  @JsonProperty("value")
  private final String value;
}
