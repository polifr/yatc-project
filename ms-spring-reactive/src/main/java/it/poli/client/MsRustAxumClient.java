package it.poli.client;

import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MsRustAxumClient {

  private final WebClient webClient;

  public MsRustAxumClient(
      WebClient.Builder webClientBuilder,
      ServletBearerExchangeFilterFunction servletBearerExchangeFilterFunction) {
    this.webClient =
        webClientBuilder
            .baseUrl("http://ms-rust-axum:8080")
            .filter(servletBearerExchangeFilterFunction)
            .build();
  }

  public Mono<String> getTestV1() {
    return webClient.get().uri("/api/rust-axum/test/v1").retrieve().bodyToMono(String.class);
  }
}
