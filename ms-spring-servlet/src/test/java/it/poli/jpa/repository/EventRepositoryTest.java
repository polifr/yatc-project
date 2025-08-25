package it.poli.jpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import it.poli.jpa.model.Event;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EventRepositoryTest {

  @Autowired EventRepository eventRepository;

  @Test
  void testInjiection() {
    assertNotNull(eventRepository, "eventRepository null");
  }

  @Test
  void testFindAllEmpty() {
    List<Event> items = eventRepository.findAll();
    assertNotNull(items, "items null");
    assertTrue(items.isEmpty(), "items not empty");
  }

  @Test
  void testSaveAndFindAllOne() {
    Event item = eventRepository.save(Event.builder().message("TEST MESSAGE").build());
    assertNotNull(item.getId(), "id null");
    List<Event> items = eventRepository.findAll();
    assertNotNull(items, "items null");
    assertFalse(items.isEmpty(), "items empty");
    assertEquals(1, items.size(), "wrong items size");
    assertEquals("TEST MESSAGE", items.getFirst().getMessage(), "wrong item message");
  }
}
