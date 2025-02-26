package it.poli.filter;

import java.time.Duration;
import java.util.Optional;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CsrfHeaderFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    Mono<CsrfToken> csrfToken =
        Optional.<Mono<CsrfToken>>ofNullable(exchange.getAttribute(CsrfToken.class.getName()))
            .orElse(Mono.empty());

    return csrfToken
        .doOnSuccess(
            (CsrfToken token) -> {
              ResponseCookie cookie =
                  ResponseCookie.from("XSRF-TOKEN", token.getToken())
                      .maxAge(Duration.ofHours(1))
                      .httpOnly(false)
                      .path("/")
                      .build();
              exchange.getResponse().getCookies().set("XSRF-TOKEN", cookie);
            })
        .then(chain.filter(exchange));
  }
}
