package com.sportsevents.eventsservicespring.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.sportsevents.eventsservicespring.api.model.ClosedEventModel;
import com.sportsevents.eventsservicespring.api.model.EventModel;
import com.sportsevents.eventsservicespring.api.model.UpdatePlayersModel;
import com.sportsevents.eventsservicespring.entity.Event;
import com.sportsevents.eventsservicespring.entity.EventStatus;
import com.sportsevents.eventsservicespring.entity.EventType;
import com.sportsevents.eventsservicespring.entity.EventsRepo;
import com.sportsevents.eventsservicespring.utils.EventsUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventsRepo eventsRepo;

    @Override
    @Transactional
    public Optional<Event> createEvent(EventModel eventModel) throws IllegalArgumentException{
        Optional<Event> sportsEventOpt = EventsUtils.getSportsEvent(eventModel);
        sportsEventOpt.ifPresent(this::saveEvent);
        return sportsEventOpt;
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
    public Optional<Event> getEvent(String type, Long id) {
        return Optional.ofNullable((Event) eventsRepo.findById(id).get());
    }

    @Override
    public List<Event> getEvents(String type) {
        Optional<EventType> optType = EventsUtils.getType(type);
        if(optType.isPresent()){
            return eventsRepo.findByType(optType.get().toString()).stream().map(event -> (Event) event)
                        .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        eventsRepo.deleteById(id); 
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
            if(isMaxPlayersReached(event, EventsUtils.getMaxPlayers(event.getType()))){
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

    private boolean isMaxPlayersReached(Event event, Optional<Integer> maxOpt) {
        return event.getMaxPlayers() <= maxOpt.get();
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
}
