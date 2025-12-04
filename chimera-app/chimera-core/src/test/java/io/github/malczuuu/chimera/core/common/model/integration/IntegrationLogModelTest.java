package io.github.malczuuu.chimera.core.common.model.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class IntegrationLogModelTest {

  private final JsonMapper mapper =
      JsonMapper.builder()
          .findAndAddModules()
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
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

    assertThat(log.getId()).isEqualTo("log-123");
    assertThat(log.getLabel()).isEqualTo("weather");
    assertThat(log.getDirection()).isEqualTo("out");
    assertThat(log.getProtocol()).isEqualTo("http");
    assertThat(log.getMethod()).isEqualTo("GET");
    assertThat(log.getAddress()).isEqualTo("https://api.example.com/weather");
    assertThat(log.getRequestAttributes()).isEqualTo("timeout=5000");
    assertThat(log.getRequestBody()).isEqualTo("{\"query\":\"Krakow\"}");
    assertThat(log.getRequestTimestamp())
        .isEqualTo(Instant.parse("2025-12-04T10:00:00Z").atOffset(ZoneOffset.UTC));
    assertThat(log.getResponseBody()).isEqualTo("{\"temp\":15.5}");
    assertThat(log.getResponseStatus()).isEqualTo("200 OK");
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
        }
        """;
    assertThat(mapper.readTree(json)).isEqualTo(mapper.readTree(expectedJson));
  }
}
