package io.github.malczuuu.chimera.core.domain.weather;

import java.util.List;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

  List<WeatherEntity> findAllByCityOrderByTimestampDesc(String city, Limit limit);
}
