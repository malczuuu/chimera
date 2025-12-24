package io.github.malczuuu.chimera.core.infrastructure.rest.problem;

import io.github.problem4j.core.Problem;
import io.github.problem4j.core.ProblemContext;
import io.github.problem4j.spring.webmvc.AdviceWebMvcInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.context.request.WebRequest;

public class LoggingAdviceMvcInspector implements AdviceWebMvcInspector {

  private static final Logger log = LoggerFactory.getLogger(LoggingAdviceMvcInspector.class);

  @Override
  public void inspect(
      ProblemContext context,
      Problem problem,
      Exception ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    LoggingEventBuilder builder = log.atError();

    if (log.isDebugEnabled()) {
      builder = builder.setCause(ex);
    }

    builder.log(
        "Resolved type={}, status={}, exception={}",
        problem.getType(),
        status.value(),
        ex.getClass().getName());
  }
}
