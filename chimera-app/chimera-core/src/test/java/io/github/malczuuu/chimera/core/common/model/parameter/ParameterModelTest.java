package io.github.malczuuu.chimera.core.common.model.parameter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

class ParameterModelTest {

  private final JsonMapper mapper = JsonMapper.builder().build();

  @Test
  void shouldDeserializeFromJson() throws Exception {
    String json =
        """
        {
          "id" : "param-123",
          "code" : "MAX_RETRIES",
          "value" : "5"
        }
        """;

    ParameterModel parameter = mapper.readValue(json, ParameterModel.class);

    assertThat(parameter.getId()).isEqualTo("param-123");
    assertThat(parameter.getCode()).isEqualTo("MAX_RETRIES");
    assertThat(parameter.getValue()).isEqualTo("5");
  }

  @Test
  void shouldSerializeToJson() throws Exception {
    ParameterModel parameter =
        ParameterModel.builder().id("param-456").code("TIMEOUT").value("30s").build();

    String json = mapper.writeValueAsString(parameter);

    String expectedJson =
        """
        {
          "id" : "param-456",
          "code" : "TIMEOUT",
          "value" : "30s"
        }
        """;
    assertThat(mapper.readTree(json)).isEqualTo(mapper.readTree(expectedJson));
  }
}
