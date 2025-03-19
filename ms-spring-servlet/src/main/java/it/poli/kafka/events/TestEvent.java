package it.poli.kafka.events;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TestEvent {

  private String message;

  @Builder.Default private ZonedDateTime creationTime = ZonedDateTime.now();
}
