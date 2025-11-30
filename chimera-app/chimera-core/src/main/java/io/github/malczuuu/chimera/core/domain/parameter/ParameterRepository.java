package io.github.malczuuu.chimera.core.domain.parameter;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ParameterRepository extends JpaRepository<ParameterEntity, Long> {

  Optional<ParameterEntity> findByCode(String code);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT p FROM ParameterEntity p WHERE p.code = :code")
  Optional<ParameterEntity> lockByCode(String code);
}
