package io.github.malczuuu.chimera.testkit;

import io.github.malczuuu.chimera.testkit.container.PostgresContainerConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag("testcontainers")
@Import({PostgresContainerConfiguration.class})
public @interface PostgresContainerTest {}
