package it.poli.filter.factory;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

import io.micrometer.tracing.Tracer;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TraceIdResponseHeaderGatewayFilterFactory
    extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

  @Autowired private Tracer tracer;

  public TraceIdResponseHeaderGatewayFilterFactory() {
    super(NameConfig.class);
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Collections.singletonList(NAME_KEY);
  }

  @Override
  public GatewayFilter apply(NameConfig config) {
    return new GatewayFilter() {

      @Override
      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> addHeader(exchange, config)));
      }

      @Override
      public String toString() {
        return filterToStringCreator(TraceIdResponseHeaderGatewayFilterFactory.this)
            .append("name", config.getName())
            .toString();
      }
    };
  }

  void addHeader(ServerWebExchange exchange, NameConfig config) {
    if (!exchange.getResponse().isCommitted()) {
      exchange
          .getResponse()
          .getHeaders()
          .add(
              config.getName(),
              ServerWebExchangeUtils.expand(
                  exchange, tracer.currentTraceContext().context().traceId()));
    }
  }
}
