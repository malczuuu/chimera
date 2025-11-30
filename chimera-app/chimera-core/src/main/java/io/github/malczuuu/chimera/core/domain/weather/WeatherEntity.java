package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.common.Discriminator;
import io.github.malczuuu.chimera.core.infrastructure.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "weather")
@SequenceGenerator(name = "seq_weather", sequenceName = "seq_weather", allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", length = 1)
@DiscriminatorValue(Discriminator.CORE)
public class WeatherEntity extends AbstractEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_weather")
  private Long id;

  @Column(name = "city", length = 256)
  private String city;

  @Column(name = "temperature")
  private Double temperature;

  @Column(name = "description", length = 256)
  private String description;

  @Column(name = "labels", length = 1024)
  private String labels;

  @Column(name = "timestamp")
  private Instant timestamp;
}
