package io.github.malczuuu.chimera.core.domain.integration;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IntegrationLogRepository extends JpaRepository<IntegrationLogEntity, Long> {

  List<IntegrationLogEntity> findAllByOrderByRequestTimestampDesc();
}
