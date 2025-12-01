package io.github.malczuuu.chimera.app.flyway;

import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {FlywayApplication.class})
@PostgresContainerTest
class FlywayApplicationTest {

  @Test
  void contextLoads() {}
}
