package com.sportsevents.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.api.model.LeaderboardEntryModel;
import com.sportsevents.entity.Event;
import com.sportsevents.entity.EventStatus;
import com.sportsevents.entity.EventsRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventsRepo eventsRepo;
    
    @Override
    @Transactional
    public Optional<Event> createEvent(EventModel eventModel) throws IllegalArgumentException{
        Optional<Event> eventOpt = EventFactory.getSportsEvent(eventModel);
        eventOpt.ifPresent(this::saveEvent);
        return eventOpt;
    }

    private void saveEvent(Event sportsEvent) throws IllegalArgumentException{
        Event event = (Event) sportsEvent;

        if(event.getScheduledDate().isAfter(LocalDateTime.now())){
            eventsRepo.save(event);
            logger.info("Persisted event with id {}, event description: {}", event.getId(), event.toString());
        }else{
            logger.info("Unable to persist event with id {}, past event date: {}", event.getId(), event.getScheduledDate().toString());
            throw new IllegalArgumentException("Unable to persist event, past event date");
        }
        
    }

    @Override
    public Optional<Event> getEvent(Long id) {
        return Optional.ofNullable((Event) eventsRepo.findById(id).get());
    }

    @Override
    public List<Event> getEvents(String type, String userName) {
        if(type.equalsIgnoreCase("home")){
            Iterable<Event> iterable = eventsRepo.findAll();
            List<Event> events = new ArrayList<>();
            iterable.forEach( e -> {
                if(e.getPlayers() != null){
                    if(Arrays.asList(e.getPlayers()).contains(userName)){
                        events.add(e);
                    }
                }
            });
            return events;
            // return Stream.of(events)
            //     .map(event -> (Event) event)
            //     .filter(event -> {
            //         return event.getPlayers() != null ? Arrays.asList(event.getPlayers()).contains(userName) : false;
            //     })
            //     .collect(Collectors.toList());
        }

        LocalDateTime now = LocalDateTime.now();
        if(type.equalsIgnoreCase("future")){
            return eventsRepo.findByScheduledDateAfter(now);
        }
        if(type.equalsIgnoreCase("past")){
            return eventsRepo.findByScheduledDateBefore(now);
        }

        return Collections.emptyList();
        
        
        // return events;
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        try{
            logger.info("Deleting event with id {} ", id);
            eventsRepo.deleteById(id); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public boolean closeEvent(ClosedEventModel closedEventModel) {
        Optional<Event> optEvent = eventsRepo.findById(closedEventModel.getEventId());

        if (optEvent.isPresent()) {
            Event event = optEvent.get();
            if(event.getStatus().equals(EventStatus.CLOSED.toString())){
                logger.info("Event with id {} is already closed, update not required", closedEventModel.getEventId());
                return false;
            }
            eventsRepo.closeEvent(closedEventModel.getWinner(),closedEventModel.getClosedBy(),EventStatus.CLOSED.toString(),
                            closedEventModel.getClosedDate(),closedEventModel.getEventId());
            logger.info("Closed event with id {} ", closedEventModel.getEventId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addPlayer(UpdatePlayersModel updatePlayersModel) {
        Optional<Event> optEvent = eventsRepo.findById(updatePlayersModel.getEventId());

        if (optEvent.isPresent()) {
            Event event = optEvent.get();
            if(isMaxPlayersReached(event, EventFactory.getMaxPlayers(event.getType()).get())){
                logger.info("Max players reached for event id {}, can't add another player", updatePlayersModel.getEventId());
                return false;
            } 
            // if(Arrays.stream(chessEvent.getPlayers()).anyMatch(updatePlayersModel.getPlayerName()::equals)){
            //     logger.info("Player {} already exists for event id {}", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
            //     return false;
            // }
            eventsRepo.updatePlayers(addPlayer(event.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
            logger.info("Added player {}, event id: {} ", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removePlayer(UpdatePlayersModel updatePlayersModel) {
        Optional<Event> optEvent = eventsRepo.findById(updatePlayersModel.getEventId());

        if (optEvent.isPresent()) {
            Event event = optEvent.get();
            eventsRepo.updatePlayers(removePlayer(event.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
            logger.info("Removed player {} from event with id {}", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
            return true;
        } else {
            return false;
        }
    }

    private String[] addPlayer(String[] players, String playerName) {
        if(players == null){
            players = new String[1]; 
            players[0] = playerName;
            return players;
        }else{
            String[] newArray = new String[players.length + 1];
            for(int i=0; i< players.length; i++){
                if(playerName.equals(players[i])){
                    return players;
                }
                newArray[i] = players[i];
            }
            newArray[players.length] = playerName;
            return newArray;
        }
    }

    private boolean isMaxPlayersReached(Event event, long max) {
        return event.getMaxPlayers() < max;
    }

    private String[] removePlayer(String[] players, String playerName) {
        if(players == null){
            return players;
        }else{
            String arrayvals = "";

            String[] newArray = new String[players.length - 1];
            boolean flag = false;
            for(int i=0, j=0; i < players.length; i++){
                if(playerName.equals(players[i])){
                    flag = true;
                }else{
                    if(!(j == players.length - 1)){
                        
                        newArray[j] = players[i];
                        arrayvals+=" / " + newArray[j];
                        j++;
                    }
                }
            }
            if(flag){
                System.out.println("returning new array: " + arrayvals);
                return newArray;
            }else{
                System.out.println("returning new players: " + players + "new array: " + arrayvals);
                return players;
            }
        }
    }

    @Override
    public Optional<List<LeaderboardEntryModel>> getLeaderboard() {
        List<LeaderboardEntryModel> leaderboardEntryList = new ArrayList<>();
        List<Object[]> leaderboard = eventsRepo.getLeaderboard(PageRequest.of(0, 20));
        long rank = 1;
        for(Object[] row : leaderboard ){
            LeaderboardEntryModel leaderboardEntryModel = new LeaderboardEntryModel();
            leaderboardEntryModel.setRank(rank);
            leaderboardEntryModel.setPlayerName(String.valueOf(row[0]));
            leaderboardEntryModel.setGamesWon(Long.valueOf(String.valueOf(row[1])));
            leaderboardEntryList.add(leaderboardEntryModel);
            rank++;
        }
        return leaderboardEntryList.isEmpty() ? Optional.empty() : Optional.of(leaderboardEntryList);
    }
}
