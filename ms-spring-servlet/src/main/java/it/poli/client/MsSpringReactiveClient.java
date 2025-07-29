package it.poli.client;

import java.util.List;
import java.util.function.Consumer;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MsSpringReactiveClient {

  private final RestClient restClient;

  public MsSpringReactiveClient(
      RestClient.Builder restClientBuilder,
      Consumer<List<ClientHttpRequestInterceptor>> clientHttpRequestInterceptorList) {
    restClient =
        restClientBuilder
            .baseUrl("http://ms-spring-reactive:8080")
            .requestInterceptors(clientHttpRequestInterceptorList)
            .build();
  }

  public String getTestV1() {
    return restClient.get().uri("/api/spring-reactive/test/v1").retrieve().body(String.class);
  }
}
