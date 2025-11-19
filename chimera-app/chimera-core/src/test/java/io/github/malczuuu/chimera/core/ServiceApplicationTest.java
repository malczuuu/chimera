package io.github.malczuuu.chimera.core;

import io.github.malczuuu.chimera.core.infrastructure.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {TestApp.class})
@Import(TestcontainersConfiguration.class)
class ServiceApplicationTest {

  @Test
  void contextLoads() {}
}
