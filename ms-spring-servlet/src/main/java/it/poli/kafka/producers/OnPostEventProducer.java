package it.poli.kafka.producers;

import it.poli.kafka.events.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OnPostEventProducer {

  private final StreamBridge streamBridge;

  public boolean publishTestEvent(TestEvent testEvent) {
    return streamBridge.send("yatc-test-topic", testEvent);
  }
}
