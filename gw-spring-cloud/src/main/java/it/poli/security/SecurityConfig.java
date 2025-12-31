package it.poli.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.micrometer.metrics.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.micrometer.metrics.autoconfigure.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.boot.security.autoconfigure.actuate.web.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.session.data.redis.ReactiveRedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

  @Bean
  ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
    return new WebSessionServerOAuth2AuthorizedClientRepository();
  }

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http,
      ReactiveClientRegistrationRepository clientRegistrationRepository,
      ReactiveRedisSessionRepository sessionRepository) {
    http.formLogin(FormLoginSpec::disable);
    http.httpBasic(HttpBasicSpec::disable);

    http.authorizeExchange(
        exchange ->
            exchange
                .matchers(
                    EndpointRequest.to(
                        InfoEndpoint.class,
                        HealthEndpoint.class,
                        MetricsEndpoint.class,
                        PrometheusScrapeEndpoint.class))
                .permitAll());

    http.authorizeExchange(exchange -> exchange.anyExchange().authenticated());

    http.oauth2Login(Customizer.withDefaults());

    http.csrf(
        csrf ->
            csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler()));

    http.logout(
        logout ->
            logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)));

    return http.build();
  }

  private static ServerLogoutSuccessHandler oidcLogoutSuccessHandler(
      ReactiveClientRegistrationRepository clientRegistrationRepository) {
    OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler =
        new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
    logoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
    return logoutSuccessHandler;
  }
}
