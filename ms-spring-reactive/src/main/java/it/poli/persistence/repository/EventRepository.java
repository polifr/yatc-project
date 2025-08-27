package it.poli.persistence.repository;

import it.poli.persistence.domain.Event;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.cord.publisher.Flux;

public interface EventRepository extends ReactiveMongoRepository<Event, Long> {
  Flux<Event> findByMessage(String message);
}
