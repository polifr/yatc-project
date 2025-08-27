package it.poli.jpa.repository;

import static org.junit.jupiter.api.Assertions.*;

import it.poli.jpa.domain.Event;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class EventRepositoryTest {

  @Autowired EventRepository eventRepository;

  @Autowired TestEntityManager entityManager;

  @Test
  void testInjiection() {
    assertNotNull(eventRepository, "eventRepository null");
    assertNotNull(entityManager, "entityManager null");
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

  @Test
  void testSaveAndFindById() {
    Event item = eventRepository.save(Event.builder().message("TEST MESSAGE").build());
    assertNotNull(item.getId(), "id null");
    Optional<Event> inserted = eventRepository.findById(item.getId());
    assertNotNull(inserted, "inserted null");
    assertFalse(inserted.isEmpty(), "inserted empty");
    assertEquals("TEST MESSAGE", inserted.get().getMessage(), "wrong item message");
  }
}
