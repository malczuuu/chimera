package io.github.malczuuu.chimera.core.common.model.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class IntegrationLogModelTest {

  private final JsonMapper mapper =
      JsonMapper.builder()
          .findAndAddModules()
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
          .configure(SerializationFeature.INDENT_OUTPUT, true)
          .build();

  @Test
  void shouldDeserializeFromJson() throws Exception {
    String json =
        """
        {
          "id" : "log-123",
          "label" : "weather",
          "direction" : "out",
          "protocol" : "http",
          "method" : "GET",
          "address" : "https://api.example.com/weather",
          "requestAttributes" : "timeout=5000",
          "requestBody" : "{\\"query\\":\\"Krakow\\"}",
          "requestTimestamp" : "2025-12-04T10:00:00Z",
          "responseBody" : "{\\"temp\\":15.5}",
          "responseStatus" : "200 OK"
        }
        """;

    IntegrationLogModel log = mapper.readValue(json, IntegrationLogModel.class);

    assertEquals("log-123", log.getId());
    assertEquals("weather", log.getLabel());
    assertEquals("out", log.getDirection());
    assertEquals("http", log.getProtocol());
    assertEquals("GET", log.getMethod());
    assertEquals("https://api.example.com/weather", log.getAddress());
    assertEquals("timeout=5000", log.getRequestAttributes());
    assertEquals("{\"query\":\"Krakow\"}", log.getRequestBody());
    assertEquals(
        OffsetDateTime.of(2025, 12, 4, 10, 0, 0, 0, ZoneOffset.UTC), log.getRequestTimestamp());
    assertEquals("{\"temp\":15.5}", log.getResponseBody());
    assertEquals("200 OK", log.getResponseStatus());
  }

  @Test
  void shouldSerializeToJson() throws Exception {
    IntegrationLogModel log =
        IntegrationLogModel.builder()
            .id("log-456")
            .label("payment")
            .direction("out")
            .protocol("https")
            .method("POST")
            .address("https://api.payment.com/charge")
            .requestAttributes("auth=Bearer token123")
            .requestBody("{\"amount\":100}")
            .requestTimestamp(OffsetDateTime.of(2025, 12, 4, 15, 30, 0, 0, ZoneOffset.UTC))
            .responseBody("{\"status\":\"success\"}")
            .responseStatus("201 Created")
            .build();

    String json = mapper.writeValueAsString(log);

    String expectedJson =
        """
        {
          "id" : "log-456",
          "label" : "payment",
          "direction" : "out",
          "protocol" : "https",
          "method" : "POST",
          "address" : "https://api.payment.com/charge",
          "requestAttributes" : "auth=Bearer token123",
          "requestBody" : "{\\"amount\\":100}",
          "requestTimestamp" : "2025-12-04T15:30:00Z",
          "responseBody" : "{\\"status\\":\\"success\\"}",
          "responseStatus" : "201 Created"
        }""";
    assertThat(json).isEqualTo(expectedJson);
  }
}
