package io.github.malczuuu.chimera.core.common.model.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Content<T> {

  private final List<T> content;

  @JsonCreator
  public Content(@JsonProperty("content") List<T> content) {
    this.content = content;
  }

  @JsonProperty("content")
  public List<T> getContent() {
    return content;
  }
}
