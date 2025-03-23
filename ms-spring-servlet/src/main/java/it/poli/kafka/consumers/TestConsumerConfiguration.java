package it.poli.kafka.consumers;

import it.poli.kafka.events.TestEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TestConsumerConfiguration {

  @Bean
  Consumer<TestEvent> testEventConsumer() {
    log.info("Creazione bean testEventConsumer");
    return c -> log.info("Event received: {}", c);
  }
}
