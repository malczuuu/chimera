package io.github.malczuuu.chimera.core.common.exception;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import io.github.malczuuu.problem4j.core.ProblemStatus;

public class ResourceNotFoundException extends ProblemException {

  public ResourceNotFoundException() {
    this(null, (String) null);
  }

  public ResourceNotFoundException(String resource, Number id) {
    this(resource, String.valueOf(id));
  }

  public ResourceNotFoundException(String resource, String id) {
    super(
        Problem.builder()
            .status(ProblemStatus.NOT_FOUND)
            .detail("Resource not found")
            .extension("resource", resource)
            .extension("id", id)
            .build());
  }
}
