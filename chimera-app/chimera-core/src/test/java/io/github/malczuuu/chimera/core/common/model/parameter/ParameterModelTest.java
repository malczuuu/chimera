package io.github.malczuuu.chimera.core.common.model.parameter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

class ParameterModelTest {

  private final JsonMapper mapper =
      JsonMapper.builder().configure(SerializationFeature.INDENT_OUTPUT, true).build();

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

    assertEquals("param-123", parameter.getId());
    assertEquals("MAX_RETRIES", parameter.getCode());
    assertEquals("5", parameter.getValue());
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
        }""";
    assertThat(json).isEqualTo(expectedJson);
  }
}
