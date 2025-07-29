package it.poli.client.filter;

import java.util.List;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Configuration(proxyBeanMethods = false)
public class FilterFunctionConfiguration {

  @Bean
  ServletBearerExchangeFilterFunction servletBearerExchangeFilterFunction() {
    return new ServletBearerExchangeFilterFunction();
  }

  @Bean
  Consumer<List<ExchangeFilterFunction>> exchangeFilterFunctionList() {
    return (List<ExchangeFilterFunction> filters) -> {
      filters.add(new ServletBearerExchangeFilterFunction());
    };
  }
}
