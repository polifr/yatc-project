package it.poli.kafka.consumers;

import it.poli.kafka.events.TestEvent;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestConsumerConfiguration {

  @Bean
  Consumer<TestEvent> testEventConsumer() {
    log.info("Creazione bean testEventConsumer");
    return testEvent -> log.info("Event received: {}", testEvent);
  }
}
