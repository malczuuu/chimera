package io.github.malczuuu.chimera.ext.app.service.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.malczuuu.chimera.core.common.model.integration.IntegrationLogModel;
import io.github.malczuuu.chimera.core.domain.integration.IntegrationLogEntity;
import io.github.malczuuu.chimera.ext.app.service.ExtServiceApplication;
import io.github.malczuuu.chimera.ext.app.service.common.model.ExtIntegrationLogModel;
import io.github.malczuuu.chimera.ext.app.service.domain.integration.ExtIntegrationLogEntity;
import io.github.malczuuu.chimera.ext.app.service.domain.integration.ExtIntegrationLogRepository;
import io.github.malczuuu.chimera.testkit.PostgresContainerTest;
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
@SpringBootTest(classes = {ExtServiceApplication.class})
@AutoConfigureMockMvc
@PostgresContainerTest
class ExtIntegrationLogControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ExtIntegrationLogRepository integrationLogRepository;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  void beforeEach() {
    integrationLogRepository.deleteAll();
  }

  @Test
  void givenExtLogRequest_whenCreateIntegrationLog_thenShouldUseExtModels() throws Exception {
    IntegrationLogModel requestBody =
        ExtIntegrationLogModel.builder()
            .traceId("trace-12345")
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

    var entities = integrationLogRepository.findAllByOrderByRequestTimestampDesc();
    assertThat(entities).isNotEmpty();

    IntegrationLogEntity savedLogEntity = entities.get(0);
    assertThat(savedLogEntity).isInstanceOf(ExtIntegrationLogEntity.class);
    assertThat(((ExtIntegrationLogEntity) savedLogEntity).getTraceId()).isEqualTo("trace-12345");
  }
}
