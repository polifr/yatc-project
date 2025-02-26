package it.poli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class IndexController {

  @GetMapping(path = {"/", "/index.htm", "/index.html"})
  public Mono<Rendering> loginTitolare() {
    return Mono.just(Rendering.view("index").build());
  }
}
