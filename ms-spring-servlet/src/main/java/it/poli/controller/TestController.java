package it.poli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spring-servlet")
public class TestController {

  @GetMapping("/test/v1")
  public ResponseEntity<String> testV1() {
    return ResponseEntity.ok("This is a Spring Servlet microservice!");
  }
}
