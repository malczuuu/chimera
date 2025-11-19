package io.github.malczuuu.chimera.core.infrastructure.context;

public interface VersionAwareEntity extends VersionAware {

  void setVersion(Long version);
}
