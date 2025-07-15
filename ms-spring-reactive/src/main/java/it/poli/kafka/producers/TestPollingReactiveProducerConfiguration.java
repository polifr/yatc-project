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
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestPollingReactiveProducerConfiguration {

  @PollableBean
  Supplier<Flux<Message<TestEvent>>> testPollingEventReactiveProducer() {
    log.info("Creazione bean testEventProducer");
    return () -> {
      TestEvent event = TestEvent.builder().message(UUID.randomUUID().toString()).build();
      log.info("Sending event {}", event);
      return Flux.just(CloudEventMessageBuilder.withData(event).build());
    };
  }
}
