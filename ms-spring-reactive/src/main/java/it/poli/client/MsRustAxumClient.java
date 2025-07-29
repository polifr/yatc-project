package it.poli.client;

import java.util.List;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MsRustAxumClient {

  private final WebClient webClient;

  public MsRustAxumClient(
      WebClient.Builder webClientBuilder,
      Consumer<List<ExchangeFilterFunction>> exchangeFilterFunctionList) {
    this.webClient =
        webClientBuilder
            .baseUrl("http://ms-rust-axum:8080")
            .filters(exchangeFilterFunctionList)
            .build();
  }

  public Mono<String> getTestV1() {
    return webClient.get().uri("/api/rust-axum/test/v1").retrieve().bodyToMono(String.class);
  }
}
