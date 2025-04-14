package it.poli.kafka.consumers;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import it.poli.kafka.events.TestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestReactiveConsumerConfiguration {

  @Bean
  Consumer<Flux<Message<TestEvent>>> testEventReactiveConsumer() {
    return input -> {
      log.info("Check");
    };
  }
}
