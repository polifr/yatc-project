package it.poli.client;

import it.poli.client.interceptor.BearerTokenInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MsSpringReactiveClient {

  private final RestClient restClient;

  public MsSpringReactiveClient(
      RestClient.Builder restClientBuilder, BearerTokenInterceptor bearerTokenInterceptor) {
    restClient =
        restClientBuilder
            .baseUrl("http://ms-spring-reactive:8080")
            .requestInterceptor(bearerTokenInterceptor)
            .build();
  }

  public String getTestV1() {
    return restClient.get().uri("/api/spring-reactive/test/v1").retrieve().body(String.class);
  }
}
