package it.poli.controller;

import static org.junit.jupiter.api.Assertions.*;

import it.poli.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(TestController.class)
@Import(SecurityConfig.class)
class TestControllerTest {

  @MockitoBean ReactiveJwtDecoder reactiveJwtDecoder;

  @Autowired private WebTestClient webClient;

  @Test
  void testInjection() {
    assertNotNull(webClient, "webClient null");
  }

  @Test
  void testTestEndpointV1Unauthorized() {
    webClient.get().uri("/api/spring-reactive/test/v1").exchange().expectStatus().isUnauthorized();
  }

  @Test
  @WithMockUser
  void testTestEndpointV1Ok() {
    webClient
        .get()
        .uri("/api/spring-reactive/test/v1")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .isEqualTo("This is a Spring Reactive microservice!");
  }
}
