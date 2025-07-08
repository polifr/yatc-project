package it.poli.controller;

import it.poli.controller.model.MessageModel;
import it.poli.kafka.events.TestEvent;
import it.poli.kafka.producers.OnPostEventReactiveProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/spring-reactive/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

  private final OnPostEventReactiveProducer onPostEventProducer;

  @PostMapping("/v1")
  public Mono<ResponseEntity<Void>> sendMessage(@RequestBody Mono<MessageModel> mono) {
    return mono.map(
        message -> {
          log.debug("Ricevuta richiesta di invio del messaggio >{}<", message);
          boolean ret =
              onPostEventProducer.publishTestEvent(
                  TestEvent.builder().message(message.getMessageBody()).build());
          log.debug("Esito di invio del messaggio >{}<", ret);
          return ret ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
        });
  }
}
