package io.github.malczuuu.chimera.core.infrastructure.data;

import io.github.malczuuu.chimera.core.common.context.VersionAwareEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Auditable;

@MappedSuperclass
public abstract class AbstractEntity
    implements Auditable<String, Long, Instant>, VersionAwareEntity {

  @Column(name = "created_by", length = 256)
  private String createdBy;

  @Column(name = "created_date")
  private Instant createdDate;

  @Column(name = "last_modified_by", length = 256)
  private String lastModifiedBy;

  @Column(name = "last_modified_date")
  private Instant lastModifiedDate;

  @Version
  @Column(name = "version")
  private Long version;

  @Override
  public Optional<String> getCreatedBy() {
    return Optional.ofNullable(createdBy);
  }

  @Override
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @Override
  public Optional<Instant> getCreatedDate() {
    return Optional.ofNullable(createdDate);
  }

  @Override
  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public Optional<String> getLastModifiedBy() {
    return Optional.ofNullable(lastModifiedBy);
  }

  @Override
  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  @Override
  public Optional<Instant> getLastModifiedDate() {
    return Optional.ofNullable(lastModifiedDate);
  }

  @Override
  public void setLastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public abstract Long getId();

  public String getIdAsString() {
    return getId() != null ? getId().toString() : null;
  }

  @Override
  public boolean isNew() {
    return getId() == null;
  }

  @Override
  public Long getVersion() {
    return version;
  }

  @Override
  public void setVersion(Long version) {
    this.version = version;
  }
}
