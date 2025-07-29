package it.poli.client;

import it.poli.client.interceptor.BearerTokenInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MsRustAxumClient {

  private final RestClient restClient;

  public MsRustAxumClient(
      RestClient.Builder restClientBuilder, BearerTokenInterceptor bearerTokenInterceptor) {
    restClient =
        restClientBuilder
            .baseUrl("http://ms-rust-axum:8080")
            .requestInterceptor(bearerTokenInterceptor)
            .build();
  }

  public String getTestV1() {
    return restClient.get().uri("/api/rust-axum/test/v1").retrieve().body(String.class);
  }
}
