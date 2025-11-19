package io.github.malczuuu.chimera.core.domain.parameter;

import io.github.malczuuu.chimera.core.common.model.parameter.ParameterModel;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class CoreParameterService implements ParameterService {

  private final ParameterFactory parameterFactory;
  private final ParameterRepository parameterRepository;

  public CoreParameterService(
      ParameterFactory parameterFactory, ParameterRepository parameterRepository) {
    this.parameterFactory = parameterFactory;
    this.parameterRepository = parameterRepository;
  }

  @Override
  public Optional<ParameterModel> findParameter(String code) {
    return parameterRepository.findByCode(code).map(this::toParameterModel);
  }

  @Transactional
  @Override
  public void setParameter(String code, String value) {
    parameterRepository
        .lockByCode(code)
        .ifPresentOrElse(
            entity -> {
              entity.setValue(value);
            },
            () -> {
              ParameterEntity parameter = parameterFactory.createEntity();
              parameter.setCode(code);
              parameter.setValue(value);
              parameterRepository.save(parameter);
            });
  }

  private ParameterModel toParameterModel(ParameterEntity parameter) {
    return new ParameterModel(parameter.getIdAsString(), parameter.getCode(), parameter.getValue());
  }
}
