package io.github.malczuuu.chimera.core.domain.parameter;

import io.github.malczuuu.chimera.core.infrastructure.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "parameters",
    indexes = {@Index(name = "idx_parameters_code", columnList = "code", unique = true)})
public class ParameterEntity extends AbstractEntity {

  @Column(name = "code", length = 256)
  private String code;

  @Column(name = "value", length = 2048)
  private String value;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
