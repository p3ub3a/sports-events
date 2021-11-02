package com.sportsevents.eventsservicespring.service;

import java.util.List;
import java.util.Optional;

import com.sportsevents.eventsservicespring.api.model.ClosedEventModel;
import com.sportsevents.eventsservicespring.api.model.EventModel;
import com.sportsevents.eventsservicespring.api.model.UpdatePlayersModel;
import com.sportsevents.eventsservicespring.entity.Event;

public interface EventService {
    Optional<Event> createEvent(EventModel event);
    Optional<Event> getEvent(String type, Long id);
    List<Event> getEvents(String type);
    void deleteEvent(Long id);
    boolean closeEvent(ClosedEventModel closedEventModel);
    boolean addPlayer(UpdatePlayersModel addPlayerModel);
    boolean removePlayer(UpdatePlayersModel addPlayerModel);
}
