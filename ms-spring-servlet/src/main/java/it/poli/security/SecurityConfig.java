package it.poli.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    PathPatternRequestMatcher.Builder mvc = PathPatternRequestMatcher.withDefaults();

    return http.formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(
                        EndpointRequest.to(
                            HealthEndpoint.class,
                            InfoEndpoint.class,
                            MetricsEndpoint.class,
                            PrometheusScrapeEndpoint.class))
                    .permitAll())
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(
                        mvc.matcher("/v3/api-docs/**"),
                        mvc.matcher("/swagger-ui.html"),
                        mvc.matcher("/swagger-ui/**"))
                    .permitAll())
        .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) // Default
        .oauth2ResourceServer(rs -> rs.jwt(withDefaults()))
        .build();
  }
}
