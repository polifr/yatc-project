package it.poli.client;

import java.util.List;
import java.util.function.Consumer;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MsRustAxumClient {

  private final RestClient restClient;

  public MsRustAxumClient(
      RestClient.Builder restClientBuilder,
      Consumer<List<ClientHttpRequestInterceptor>> clientHttpRequestInterceptorList) {
    restClient =
        restClientBuilder
            .baseUrl("http://ms-rust-axum:8080")
            .requestInterceptors(clientHttpRequestInterceptorList)
            .build();
  }

  public String getTestV1() {
    return restClient.get().uri("/api/rust-axum/test/v1").retrieve().body(String.class);
  }
}
