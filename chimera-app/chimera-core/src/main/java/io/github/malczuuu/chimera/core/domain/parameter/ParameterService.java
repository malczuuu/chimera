package io.github.malczuuu.chimera.core.domain.parameter;

import io.github.malczuuu.chimera.core.common.model.parameter.ParameterModel;
import java.util.Optional;

public interface ParameterService {

  Optional<ParameterModel> findParameter(String code);

  void setParameter(String code, String value);
}
