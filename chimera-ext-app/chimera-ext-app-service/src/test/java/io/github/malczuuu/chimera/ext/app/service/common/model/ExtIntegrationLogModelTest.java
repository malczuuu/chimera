package io.github.malczuuu.chimera.ext.app.service.common.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class ExtIntegrationLogModelTest {

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
          "responseStatus" : "200 OK",
          "traceId" : "550e8400-e29b-41d4-a716-446655440000"
        }
        """;

    ExtIntegrationLogModel log = mapper.readValue(json, ExtIntegrationLogModel.class);

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
    assertEquals("550e8400-e29b-41d4-a716-446655440000", log.getTraceId());
  }

  @Test
  void shouldSerializeToJson() throws Exception {
    ExtIntegrationLogModel log =
        (ExtIntegrationLogModel)
            ExtIntegrationLogModel.builder()
                .traceId("7c9e6679-7425-40de-944b-e07fc1f90ae7")
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
          "responseStatus" : "201 Created",
          "traceId" : "7c9e6679-7425-40de-944b-e07fc1f90ae7"
        }""";
    assertThat(json).isEqualTo(expectedJson);
  }
}
