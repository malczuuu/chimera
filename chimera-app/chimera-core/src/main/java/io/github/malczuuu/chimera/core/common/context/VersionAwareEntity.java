package io.github.malczuuu.chimera.core.common.context;

public interface VersionAwareEntity extends VersionAware {

  void setVersion(Long version);
}
