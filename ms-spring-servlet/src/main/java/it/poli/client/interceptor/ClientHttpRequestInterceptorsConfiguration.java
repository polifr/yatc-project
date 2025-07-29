package it.poli.client.interceptor;

import java.util.List;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@Configuration(proxyBeanMethods = false)
public class ClientHttpRequestInterceptorsConfiguration {

  @Bean
  BearerTokenInterceptor bearerTokenInterceptor() {
    return new BearerTokenInterceptor();
  }

  @Bean
  Consumer<List<ClientHttpRequestInterceptor>> clientHttpRequestInterceptorList() {
    return (List<ClientHttpRequestInterceptor> interceptors) -> {
      interceptors.add(new BearerTokenInterceptor());
    };
  }
}
