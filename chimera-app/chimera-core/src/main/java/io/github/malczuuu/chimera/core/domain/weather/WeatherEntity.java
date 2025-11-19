package io.github.malczuuu.chimera.core.domain.weather;

import io.github.malczuuu.chimera.core.infrastructure.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "weather")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class WeatherEntity extends AbstractEntity {

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

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLabels() {
    return labels;
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }
}
