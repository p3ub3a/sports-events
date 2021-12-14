package com.sportsevents.service;

import java.util.List;
import java.util.Optional;

import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.entity.Event;
import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.api.model.LeaderboardEntryModel;

public interface EventService {
    Optional<Event> createEvent(EventModel event);
    Optional<Event> getEvent(Long id);
    List<Event> getEvents(String type, String userName);
    void deleteEvent(Long id);
    boolean closeEvent(ClosedEventModel closedEventModel);
    boolean addPlayer(UpdatePlayersModel addPlayerModel);
    boolean removePlayer(UpdatePlayersModel addPlayerModel);
    Optional<List<LeaderboardEntryModel>> getLeaderboard();
}
