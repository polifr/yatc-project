package it.poli.configuration;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SpringDocConfigProperties.GroupConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gestione centralizzata ed automatizzata dei gruppi per swagger, ispirata a:
 * https://medium.com/@oguz.topal/central-swagger-in-spring-cloud-gateway-697a1c37b03d
 *
 * <p>Verificare anche questa altra implementazione (simile):
 * https://velog.io/@hans96/Spring-Cloud-Gateways-OpenAPI-Configuration
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
public class SpringdocConfiguration {

  @Bean
  @SuppressWarnings("unused")
  CommandLineRunner openApiGroups(
      RouteDefinitionLocator locator, SpringDocConfigProperties springDocConfigProperties) {
    log.debug("Impostazione gruppi per visualizzazione API dei microservizi...");
    return args ->
        Objects.requireNonNull(locator.getRouteDefinitions().collectList().block()).stream()
            .map(RouteDefinition::getId)
            .filter(id -> id.matches(".*-openapi"))
            .map(id -> id.replace("-openapi", ""))
            .forEach(
                id -> {
                  log.debug("Aggiunta gruppo {}", id);
                  GroupConfig groupConfig = new GroupConfig();
                  groupConfig.setGroup(id);
                  groupConfig.setDisplayName(id);
                  springDocConfigProperties.addGroupConfig(groupConfig);
                });
  }
}
