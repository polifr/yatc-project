package it.poli.kafka.consumers;

import it.poli.kafka.events.TestEvent;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestConsumerConfiguration {

  @Bean
  Consumer<Message<TestEvent>> testEventConsumer() {
    log.info("Creazione bean testEventConsumer");
    return message -> log.info("Event received: {}", message.getPayload());
  }
}
