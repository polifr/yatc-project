package it.poli.controller;

import it.poli.client.MsRustAxumClient;
import it.poli.client.MsSpringServletClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/spring-reactive/bridge")
@RequiredArgsConstructor
@Slf4j
public class BridgeController {

  private final MsSpringServletClient msSpringServletClient;
  private final MsRustAxumClient msRustAxumClient;

  @GetMapping("/spring-servlet/test/v1")
  public Mono<ResponseEntity<String>> springServlettestV1() {
    return msSpringServletClient
        .getTestV1()
        .map(
            response ->
                StringUtils.join(
                    "This is a Spring Reactive microservice calling Servlet: ", response))
        .map(response -> ResponseEntity.ok(response));
  }

  @GetMapping("/rust-axum/test/v1")
  public Mono<ResponseEntity<String>> rustAxumtestV1() {
    return msRustAxumClient
        .getTestV1()
        .map(
            response ->
                StringUtils.join("This is a Spring Reactive microservice calling Rust: ", response))
        .map(response -> ResponseEntity.ok(response));
  }
}
