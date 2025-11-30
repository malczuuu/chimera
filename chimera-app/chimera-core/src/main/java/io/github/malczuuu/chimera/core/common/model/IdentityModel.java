package io.github.malczuuu.chimera.core.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityModel {

  private final String id;

  @JsonCreator
  public IdentityModel(@JsonProperty("id") String id) {
    this.id = id;
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }
}
