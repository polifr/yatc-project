package it.poli.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spring-servlet/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

  @GetMapping("/v1")
  public ResponseEntity<String> testV1() {
    log.debug("Ricevuta richiesta su endpoint di test.");
    return ResponseEntity.ok("This is a Spring Servlet microservice!");
  }
}
