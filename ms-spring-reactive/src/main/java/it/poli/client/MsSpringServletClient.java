package it.poli.client;

import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MsSpringServletClient {

  private final WebClient webClient;

  public MsSpringServletClient(
      WebClient.Builder webClientBuilder,
      ServletBearerExchangeFilterFunction servletBearerExchangeFilterFunction) {
    this.webClient =
        webClientBuilder
            .baseUrl("http://ms-spring-servlet:8080")
            .filter(servletBearerExchangeFilterFunction)
            .build();
  }

  public Mono<String> getTestV1() {
    return webClient.get().uri("/api/spring-servlet/test/v1").retrieve().bodyToMono(String.class);
  }
}
