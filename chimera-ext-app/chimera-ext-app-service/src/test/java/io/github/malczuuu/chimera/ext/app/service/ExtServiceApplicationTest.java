package io.github.malczuuu.chimera.ext.app.service;

import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {ExtServiceApplication.class})
@PostgresContainerTest
class ExtServiceApplicationTest {

  @Test
  void contextLoads() {}
}
