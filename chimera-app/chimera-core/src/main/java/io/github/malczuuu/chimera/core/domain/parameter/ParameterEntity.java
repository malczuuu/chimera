package io.github.malczuuu.chimera.core.domain.parameter;

import io.github.malczuuu.chimera.core.common.Discriminator;
import io.github.malczuuu.chimera.core.infrastructure.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "parameters",
    indexes = {@Index(name = "idx_parameters_code", columnList = "code", unique = true)})
@SequenceGenerator(name = "seq_parameters", sequenceName = "seq_parameters", allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", length = 1)
@DiscriminatorValue(Discriminator.CORE)
public class ParameterEntity extends AbstractEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_parameters")
  private Long id;

  @Column(name = "code", length = 256)
  private String code;

  @Column(name = "value", length = 2048)
  private String value;
}
