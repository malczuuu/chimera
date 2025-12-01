package io.github.malczuuu.chimera.ext.app.flyway;

import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {ExtFlywayApplication.class})
@PostgresContainerTest
class ExtFlywayApplicationTest {

  @Test
  void contextLoads() {}
}
