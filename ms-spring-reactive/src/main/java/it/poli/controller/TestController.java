package it.poli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/spring-reactive")
public class TestController {

  @GetMapping("/test/v1")
  public Mono<ResponseEntity<String>> testV1() {
    return Mono.just(ResponseEntity.ok("This is a Spring Reactive microservice!"));
  }
}
