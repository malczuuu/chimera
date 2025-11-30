package io.github.malczuuu.chimera.migration;

import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {MigrationTestApplication.class})
@PostgresContainerTest
public class ChimeraMigrationTest {

  @Test
  void contextLoads() {}
}
