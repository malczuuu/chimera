package io.github.malczuuu.chimera.core;

import io.github.malczuuu.chimera.core.domain.ChimeraDomainConfiguration;
import io.github.malczuuu.chimera.core.infrastructure.ChimeraInfraConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({ChimeraDomainConfiguration.class, ChimeraInfraConfiguration.class})
@EntityScan(basePackageClasses = ChimeraCoreAutoConfiguration.class)
public class ChimeraCoreAutoConfiguration {}
