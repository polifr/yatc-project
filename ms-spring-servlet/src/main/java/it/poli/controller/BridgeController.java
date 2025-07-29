package it.poli.controller;

import it.poli.client.GwSpringCloudClient;
import it.poli.client.MsRustAxumClient;
import it.poli.client.MsSpringReactiveClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spring-servlet/bridge")
@RequiredArgsConstructor
@Slf4j
public class BridgeController {

  private final MsSpringReactiveClient msSpringReactiveClient;
  private final MsRustAxumClient msRustAxumClient;
  private final GwSpringCloudClient gwSpringCloudClient;

  @GetMapping("/spring-reactive/test/v1")
  public ResponseEntity<String> directSpringReactivetestV1() {
    log.debug("Ricevuta richiesta test su endpoint bridge su reactive.");
    String reactiveResponse = msSpringReactiveClient.getTestV1();
    return ResponseEntity.ok(
        "This is a Spring Servlet microservice calling Reactive: " + reactiveResponse);
  }

  @GetMapping("/gw/spring-reactive/test/v1")
  public ResponseEntity<String> gwSpringReactiveTestV1() {
    log.debug("Ricevuta richiesta test su endpoint bridge su reactive.");
    String reactiveResponse = gwSpringCloudClient.callSpringReactiveGetTestV1();
    return ResponseEntity.ok(
        "This is a Spring Servlet microservice calling Reactive (gw): " + reactiveResponse);
  }

  @GetMapping("/rust-axum/test/v1")
  public ResponseEntity<String> directRustAxumtestV1() {
    log.debug("Ricevuta richiesta test su endpoint bridge su reactive.");
    String reactiveResponse = msRustAxumClient.getTestV1();
    return ResponseEntity.ok(
        "This is a Spring Servlet microservice calling Reactive: " + reactiveResponse);
  }

  @GetMapping("/gw/rust-axum/test/v1")
  public ResponseEntity<String> gwRustAxumTestV1() {
    log.debug("Ricevuta richiesta test su endpoint bridge su reactive.");
    String reactiveResponse = gwSpringCloudClient.callRustAxumGetTestV1();
    return ResponseEntity.ok(
        "This is a Spring Servlet microservice calling Rust (gw): " + reactiveResponse);
  }
}
