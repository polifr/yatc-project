package it.poli.kafka.producers;

import it.poli.kafka.events.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OnPostEventReactiveProducer {

  private final StreamBridge streamBridge;

  public boolean publishTestEvent(TestEvent testEvent) {
    log.debug("Invio evento {} in corso...", testEvent);
    boolean ret = streamBridge.send("testEventCommonProducer-out-0", testEvent);
    log.debug("Esito invio evento {}", ret);
    return ret;
  }
}
