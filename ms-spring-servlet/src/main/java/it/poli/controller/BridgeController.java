package it.poli.controller;

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

  @GetMapping("/reactive/test/v1")
  public ResponseEntity<String> testV1() {
    log.debug("Ricevuta richiesta test su endpoint bridge su reactive.");
    String reactiveResponse = msSpringReactiveClient.getTestV1();
    return ResponseEntity.ok(
        "This is a Spring Servlet microservice calling Reactive: " + reactiveResponse);
  }
}
