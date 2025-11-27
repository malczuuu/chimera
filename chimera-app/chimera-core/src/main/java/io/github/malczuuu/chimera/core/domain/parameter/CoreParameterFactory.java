package io.github.malczuuu.chimera.core.domain.parameter;

public class CoreParameterFactory implements ParameterFactory {

  @Override
  public ParameterEntity createEntity() {
    return new ParameterEntity();
  }
}
