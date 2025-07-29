package it.poli.client.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ClientHttpRequestInterceptorsConfiguration {

  @Bean
  BearerTokenInterceptor bearerTokenInterceptor() {
    return new BearerTokenInterceptor();
  }
}
