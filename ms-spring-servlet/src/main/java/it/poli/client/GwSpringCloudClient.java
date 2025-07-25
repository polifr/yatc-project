package it.poli.client;

import it.poli.client.interceptor.BearerTokenInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GwSpringCloudClient {

  private final RestClient restClient;

  public GwSpringCloudClient(
      RestClient.Builder restClientBuilder, BearerTokenInterceptor bearerTokenInterceptor) {
    restClient =
        restClientBuilder
            .baseUrl("http://gw-spring-cloud:8080")
            .requestInterceptor(bearerTokenInterceptor)
            .build();
  }

  public String callSpringReactiveGetTestV1() {
    return restClient.get().uri("/api/spring-reactive/test/v1").retrieve().body(String.class);
  }
}
