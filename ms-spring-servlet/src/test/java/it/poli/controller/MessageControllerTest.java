package it.poli.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.poli.controller.model.MessageModel;
import it.poli.kafka.events.TestEvent;
import it.poli.kafka.producers.OnPostEventProducer;
import it.poli.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(MessageController.class)
@Import(SecurityConfig.class)
class MessageControllerTest {

  @Autowired private WebApplicationContext context;
  @Autowired private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @MockitoBean OnPostEventProducer onPostEventProducer;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  void testSendMessageUnauthorized() throws Exception {
    MessageModel message = MessageModel.builder().messageBody("TEST").build();
    this.mockMvc
        .perform(
            post("/api/spring-servlet/message/v1")
                .content(objectMapper.writeValueAsString(message)))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void testSendMessageInternalServerError() throws Exception {
    when(onPostEventProducer.publishTestEvent(any(TestEvent.class))).thenReturn(Boolean.FALSE);
    MessageModel message = MessageModel.builder().messageBody("TEST").build();
    this.mockMvc
        .perform(
            post("/api/spring-servlet/message/v1")
                .content(objectMapper.writeValueAsString(message))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }

  @Test
  @WithMockUser
  void testSendMessageOk() throws Exception {
    when(onPostEventProducer.publishTestEvent(any(TestEvent.class))).thenReturn(Boolean.TRUE);
    MessageModel message = MessageModel.builder().messageBody("TEST").build();
    this.mockMvc
        .perform(
            post("/api/spring-servlet/message/v1")
                .content(objectMapper.writeValueAsString(message))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
