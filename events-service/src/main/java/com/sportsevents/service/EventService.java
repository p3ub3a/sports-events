package com.sportsevents.service;

import java.util.List;
import java.util.Optional;

import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.entity.SportsEvent;

public interface EventService {
    Optional<SportsEvent> createEvent(EventModel event);
    Optional<SportsEvent> getEvent(String type, Long id);
    List<SportsEvent> getEvents(String type);
    boolean deleteEvent(String type, Long id);
    boolean closeEvent(ClosedEventModel closedEventModel);
    boolean addPlayer(UpdatePlayersModel addPlayerModel);
    boolean removePlayer(UpdatePlayersModel addPlayerModel);
}
