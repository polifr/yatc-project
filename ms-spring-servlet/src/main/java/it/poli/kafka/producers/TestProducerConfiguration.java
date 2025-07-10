package it.poli.kafka.producers;

import it.poli.kafka.events.TestEvent;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestProducerConfiguration {

  @PollableBean
  Supplier<Message<TestEvent>> testPollingEventProducer() {
    log.info("Creazione bean testPollingEventProducer");
    return () -> {
      TestEvent event = TestEvent.builder().message(UUID.randomUUID().toString()).build();
      log.info("Sending event {}", event);
      return CloudEventMessageBuilder.withData(event).build();
    };
  }
}
