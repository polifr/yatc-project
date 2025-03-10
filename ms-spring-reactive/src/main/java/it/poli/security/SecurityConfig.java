package it.poli.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.LogoutSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) {

    http.formLogin(FormLoginSpec::disable);
    http.httpBasic(HttpBasicSpec::disable);
    http.logout(LogoutSpec::disable);
    http.csrf(CsrfSpec::disable);

    http.authorizeExchange(
        authorize ->
            authorize
                .matchers(
                    EndpointRequest.to(
                        HealthEndpoint.class,
                        InfoEndpoint.class,
                        LoggersEndpoint.class,
                        MetricsEndpoint.class,
                        PrometheusScrapeEndpoint.class))
                .permitAll());

    http.authorizeExchange(
        authorize ->
            authorize
                .pathMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                .permitAll());

    http.authorizeExchange(authorize -> authorize.anyExchange().authenticated())
        .oauth2ResourceServer(rs -> rs.jwt(withDefaults()));

    return http.build();
  }
}
