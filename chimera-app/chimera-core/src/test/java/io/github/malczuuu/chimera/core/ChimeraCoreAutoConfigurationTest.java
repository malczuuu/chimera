package io.github.malczuuu.chimera.core;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {CoreTestApplication.class})
@PostgresContainerTest
class ChimeraCoreAutoConfigurationTest {

  /**
   * {@link Autowired} with {@code required = false}, so test won't fail during test context setup.
   * On autoconfiguration issue, it is expected for assertion to fail in {@link #contextLoads()}.
   */
  @Autowired(required = false)
  private ChimeraCoreAutoConfiguration chimeraCoreAutoConfiguration;

  @Test
  void contextLoads() {
    assertThat(chimeraCoreAutoConfiguration).isNotNull();
  }
}
