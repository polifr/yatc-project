package it.poli.kafka.producers;

import it.poli.kafka.events.TestEvent;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TestProducerConfiguration {

  @Bean
  Supplier<TestEvent> testEventProducer() {
    return () -> {
      TestEvent event = TestEvent.builder().message(UUID.randomUUID().toString()).build();
      log.info("Sending event {}", event);
      return event;
    };
  }
}
