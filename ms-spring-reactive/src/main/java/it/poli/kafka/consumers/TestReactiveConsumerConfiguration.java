package it.poli.kafka.consumers;

import it.poli.kafka.events.TestEvent;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestReactiveConsumerConfiguration {

  @Bean
  Consumer<Flux<TestEvent>> testEventReactiveConsumer() {
    log.info("Creazione bean testEventReactiveConsumer");
    return fluxEvent -> {
      fluxEvent.doOnNext(testEvent -> log.info("Event received: {}", testEvent)).subscribe();
    };
  }
}
