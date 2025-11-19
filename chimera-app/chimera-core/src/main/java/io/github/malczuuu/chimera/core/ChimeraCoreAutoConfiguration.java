package io.github.malczuuu.chimera.core;

import io.github.malczuuu.chimera.core.domain.ChimeraDomainConfiguration;
import io.github.malczuuu.chimera.core.infrastructure.ChimeraInfraConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({ChimeraDomainConfiguration.class, ChimeraInfraConfiguration.class})
public class ChimeraCoreAutoConfiguration {}
