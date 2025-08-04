package it.poli.kafka.producers;

import it.poli.kafka.events.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OnPostEventProducer {

  private final StreamBridge streamBridge;

  // Verificare impostazione destinatario fornita da
  // https://www.vincenzoracca.com/blog/framework/spring/cloud-stream/
  public boolean publishTestEvent(TestEvent testEvent) {
    log.debug("Invio evento {} in corso...", testEvent);
    boolean ret =
        streamBridge.send("onPostEventProducer-out-0", CloudEventMessageBuilder.withData(testEvent).build());
    log.debug("Esito invio evento {}", ret);
    return ret;
  }
}
