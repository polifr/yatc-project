package it.poli.controller.mapper;

import it.poli.controller.model.EventModel;
import it.poli.jpa.domain.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper extends CommonModelEntityMapper<EventModel, Event> {}
