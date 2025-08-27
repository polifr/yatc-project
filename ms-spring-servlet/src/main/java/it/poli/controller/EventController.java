package it.poli.controller;

import it.poli.controller.mapper.EventMapper;
import it.poli.controller.model.EventModel;
import it.poli.jpa.repository.EventRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spring-servlet/event")
@RequiredArgsConstructor
public class EventController {

  private final EventRepository eventRepository;

  private final EventMapper eventMapper;

  @GetMapping("/v1")
  public ResponseEntity<Collection<EventModel>> getAll() {
    return ResponseEntity.ok(eventRepository.findAll().stream().map(eventMapper::toModel).toList());
  }
}
