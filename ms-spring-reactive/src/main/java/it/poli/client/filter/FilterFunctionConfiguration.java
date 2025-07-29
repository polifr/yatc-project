package it.poli.client.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;

@Configuration(proxyBeanMethods = false)
public class FilterFunctionConfiguration {

  @Bean
  ServletBearerExchangeFilterFunction servletBearerExchangeFilterFunction() {
    return new ServletBearerExchangeFilterFunction();
  }
}
