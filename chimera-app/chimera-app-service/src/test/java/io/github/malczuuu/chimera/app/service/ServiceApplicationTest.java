package io.github.malczuuu.chimera.app.service;

import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {ServiceApplication.class})
@PostgresContainerTest
class ServiceApplicationTest {

  @Test
  void contextLoads() {}
}
