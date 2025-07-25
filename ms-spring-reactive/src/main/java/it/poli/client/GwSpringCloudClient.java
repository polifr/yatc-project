package it.poli.client;

import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GwSpringCloudClient {

  private final WebClient webClient;

  public GwSpringCloudClient(
      WebClient.Builder webClientBuilder,
      ServletBearerExchangeFilterFunction servletBearerExchangeFilterFunction) {
    this.webClient =
        webClientBuilder
            .baseUrl("http://gw-spring-cloud:8080")
            .filter(servletBearerExchangeFilterFunction)
            .build();
  }

  public Mono<String> callSpringServletGetTestV1() {
    return webClient.get().uri("/api/spring-reactive/test/v1").retrieve().bodyToMono(String.class);
  }
}
