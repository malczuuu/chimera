package io.github.malczuuu.chimera.core.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.chimera.core.CoreTestApplication;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogEntity;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogRepository;
import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles({"test"})
@SpringBootTest(classes = {CoreTestApplication.class})
@AutoConfigureMockMvc
@PostgresContainerTest
class IntegrationLogControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private IntegrationLogRepository integrationLogRepository;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void beforeEach() {
    integrationLogRepository.deleteAll();
  }

  @Test
  void givenNoLogs_whenGetIntegrationLogs_thenReturnsEmptyContent() throws Exception {
    mockMvc
        .perform(get("/api/integration-logs").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(0)));
  }

  @Test
  void givenExistingLogs_whenGetIntegrationLogs_thenReturnsAllLogs() throws Exception {
    IntegrationLogEntity log1 = createIntegrationLogEntity("http://api1.com", "GET");
    IntegrationLogEntity log2 = createIntegrationLogEntity("http://api2.com", "POST");
    integrationLogRepository.save(log1);
    integrationLogRepository.save(log2);

    mockMvc
        .perform(get("/api/integration-logs").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].address", notNullValue()))
        .andExpect(jsonPath("$.content[1].address", notNullValue()));
  }

  @Test
  void givenExistingLogs_whenGetIntegrationLogs_thenReturnsLogsOrderedByRequestTimestampDesc()
      throws Exception {
    IntegrationLogEntity older = createIntegrationLogEntity("http://api1.com", "GET");
    older.setRequestTimestamp(Instant.now().minusSeconds(7200));
    IntegrationLogEntity newer = createIntegrationLogEntity("http://api2.com", "POST");
    newer.setRequestTimestamp(Instant.now().minusSeconds(3600));
    integrationLogRepository.save(older);
    integrationLogRepository.save(newer);

    mockMvc
        .perform(get("/api/integration-logs").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].address", is("http://api2.com")))
        .andExpect(jsonPath("$.content[1].address", is("http://api1.com")));
  }

  @Test
  void givenExistingLog_whenGetIntegrationLogById_thenReturnsLog() throws Exception {
    IntegrationLogEntity entity = createIntegrationLogEntity("http://api.com", "GET");
    IntegrationLogEntity saved = integrationLogRepository.save(entity);

    mockMvc
        .perform(
            get("/api/integration-logs/{id}", saved.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(saved.getIdAsString())))
        .andExpect(jsonPath("$.address", is("http://api.com")))
        .andExpect(jsonPath("$.method", is("GET")))
        .andExpect(jsonPath("$.label", is("Test Label")))
        .andExpect(jsonPath("$.direction", is("OUTBOUND")))
        .andExpect(jsonPath("$.protocol", is("HTTP")));
  }

  @Test
  void givenNonExistingLog_whenGetIntegrationLogById_thenReturnsNotFound() throws Exception {
    mockMvc
        .perform(get("/api/integration-logs/{id}", "999999").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenValidLogRequest_whenCreateIntegrationLog_thenCreatesLogAndReturnsCreated()
      throws Exception {
    IntegrationLogModel requestBody =
        IntegrationLogModel.builder()
            .label("API Call")
            .direction("OUTBOUND")
            .protocol("HTTP")
            .method("POST")
            .address("http://external-api.com/endpoint")
            .requestAttributes("Content-Type: application/json")
            .requestBody("{\"key\":\"value\"}")
            .requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
            .responseBody("{\"result\":\"success\"}")
            .responseStatus("200")
            .build();

    MvcResult result =
        mockMvc
            .perform(
                post("/api/integration-logs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andReturn();

    String location = result.getResponse().getHeader("Location");
    assertThat(location).startsWith("/api/integration-logs/");

    long count = integrationLogRepository.count();
    assertThat(count).isEqualTo(1);
  }

  @Test
  void givenValidLogRequest_whenCreateIntegrationLog_thenPersistedLogHasCorrectData()
      throws Exception {
    OffsetDateTime timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    IntegrationLogModel requestBody =
        IntegrationLogModel.builder()
            .label("Test API")
            .direction("INBOUND")
            .protocol("HTTPS")
            .method("GET")
            .address("http://test.com/api")
            .requestAttributes("Authorization: Bearer token")
            .requestBody(null)
            .requestTimestamp(timestamp)
            .responseBody("{\"status\":\"ok\"}")
            .responseStatus("200")
            .build();

    mockMvc
        .perform(
            post("/api/integration-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
        .andExpect(status().isCreated());

    IntegrationLogEntity persisted =
        integrationLogRepository.findAllByOrderByRequestTimestampDesc().getFirst();
    assertThat(persisted.getLabel()).isEqualTo("Test API");
    assertThat(persisted.getDirection()).isEqualTo("INBOUND");
    assertThat(persisted.getProtocol()).isEqualTo("HTTPS");
    assertThat(persisted.getMethod()).isEqualTo("GET");
    assertThat(persisted.getAddress()).isEqualTo("http://test.com/api");
    assertThat(persisted.getRequestAttributes()).isEqualTo("Authorization: Bearer token");
    assertThat(persisted.getRequestBody()).isNull();
    assertThat(persisted.getResponseBody()).isEqualTo("{\"status\":\"ok\"}");
    assertThat(persisted.getResponseStatus()).isEqualTo("200");
  }

  @Test
  void givenMultipleCreatedLogs_whenGetIntegrationLogs_thenAllLogsArePresent() throws Exception {
    IntegrationLogModel log1 =
        IntegrationLogModel.builder()
            .label("Log 1")
            .direction("OUTBOUND")
            .protocol("HTTP")
            .method("GET")
            .address("http://api1.com")
            .requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
            .build();

    IntegrationLogModel log2 =
        IntegrationLogModel.builder()
            .label("Log 2")
            .direction("INBOUND")
            .protocol("HTTPS")
            .method("POST")
            .address("http://api2.com")
            .requestTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
            .build();

    mockMvc.perform(
        post("/api/integration-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(log1)));

    mockMvc.perform(
        post("/api/integration-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(log2)));

    mockMvc
        .perform(get("/api/integration-logs").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize(2)));
  }

  @Test
  void givenInvalidJson_whenCreateIntegrationLog_thenReturnsBadRequest() throws Exception {
    String invalidJson = "{invalid json}";

    mockMvc
        .perform(
            post("/api/integration-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  private IntegrationLogEntity createIntegrationLogEntity(String address, String method) {
    IntegrationLogEntity entity = new IntegrationLogEntity();
    entity.setLabel("Test Label");
    entity.setDirection("OUTBOUND");
    entity.setProtocol("HTTP");
    entity.setMethod(method);
    entity.setAddress(address);
    entity.setRequestAttributes("User-Agent: Test");
    entity.setRequestBody("{}");
    entity.setRequestTimestamp(Instant.now());
    entity.setResponseBody("{\"status\":\"ok\"}");
    entity.setResponseStatus("200");
    return entity;
  }
}
