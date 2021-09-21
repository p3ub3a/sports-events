package com.sportsevents.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.entity.Event;
import com.sportsevents.entity.EventStatus;
import com.sportsevents.entity.SportsEvent;
import com.sportsevents.entity.events.ChessEvent;
import com.sportsevents.entity.events.PingPongEvent;
import com.sportsevents.entity.events.SwimmingEvent;
import com.sportsevents.entity.events.TennisEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Override
    @Transactional
    public Optional<SportsEvent> createEvent(EventModel eventModel) throws IllegalArgumentException{
        Optional<SportsEvent> sportsEventOpt = EventFactory.getSportsEvent(eventModel);
        sportsEventOpt.ifPresent(this::saveEvent);
        return sportsEventOpt;
    }

    private void saveEvent(SportsEvent sportsEvent) throws IllegalArgumentException{
        Event event = (Event) sportsEvent;

        if(event.getScheduledDate().isAfter(LocalDateTime.now())){
            event.persist();
            logger.info("Persisted event with id {}, event description: {}", event.id, event.toString());
        }else{
            logger.info("Unable to persist event with id {}, past event date: {}", event.id, event.getScheduledDate().toString());
            throw new IllegalArgumentException("Unable to persist event, past event date");
        }
        
    }

    @Override
    public Optional<SportsEvent> getEvent(String type, Long id) {
        switch (type) {
            case "chess":
                return Optional.ofNullable((ChessEvent) ChessEvent.findById(id));
            case "swimming":
                return Optional.ofNullable((SwimmingEvent) SwimmingEvent.findById(id));
            case "tennis":
                return Optional.ofNullable((TennisEvent) TennisEvent.findById(id));
            case "pingpong":
                return Optional.ofNullable((PingPongEvent) PingPongEvent.findById(id));
            default:
                return Optional.empty();
        }
    }

    @Override
    public List<SportsEvent> getEvents(String type) {
        switch (type) {
            case "chess":
                return ChessEvent.findAll().list().stream().map(event -> (SportsEvent) event)
                        .collect(Collectors.toList());
            case "swimming":
                return SwimmingEvent.findAll().list().stream().map(event -> (SportsEvent) event)
                        .collect(Collectors.toList());
            case "tennis":
                return TennisEvent.findAll().list().stream().map(event -> (SportsEvent) event)
                        .collect(Collectors.toList());
            case "pingpong":
                return PingPongEvent.findAll().list().stream().map(event -> (SportsEvent) event)
                        .collect(Collectors.toList());
            default:
                return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public boolean deleteEvent(String type, Long id) {
        switch (type) {
            case "chess":
                try{
                    logger.info("Deleting event with id {} ", id);
                    return ChessEvent.deleteById(id);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            case "swimming":
                return SwimmingEvent.deleteById(id);
            case "tennis":
                return TennisEvent.deleteById(id);
            case "pingpong":
                return PingPongEvent.deleteById(id);
            default:
                return false;
        }
    }

    @Override
    @Transactional
    public boolean closeEvent(ClosedEventModel closedEventModel) {
        switch (closedEventModel.getType().toLowerCase()) {
            case "chess":
                Optional<ChessEvent> chessEventOpt = Optional.ofNullable(ChessEvent.findById(closedEventModel.getEventId()));
                if (chessEventOpt.isPresent()) {
                    if(chessEventOpt.get().getStatus().equals(EventStatus.CLOSED.toString())){
                        logger.info("Event with id {} is already closed, update not required", closedEventModel.getEventId());
                        return false;
                    }
                    ChessEvent.update("winner = ?1, closedBy = ?2, status = ?3, closedDate = ?4 where id = ?5", closedEventModel.getWinner(),
                        closedEventModel.getClosedBy(),EventStatus.CLOSED.toString(),closedEventModel.getClosedDate(),closedEventModel.getEventId());
                    logger.info("Closed event with id {} ", closedEventModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "swimming":
                if (Optional.ofNullable(SwimmingEvent.findById(closedEventModel.getEventId())).isPresent()) {
                    SwimmingEvent.update("winner = ?1, closedBy = ?2, status = ?3, closedDate = ?4 where id = ?5", closedEventModel.getWinner(),
                        closedEventModel.getClosedBy(),EventStatus.CLOSED.toString(),closedEventModel.getClosedDate(),closedEventModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "tennis":
                if (Optional.ofNullable(TennisEvent.findById(closedEventModel.getEventId())).isPresent()) {
                    TennisEvent.update("winner = ?1, closedBy = ?2, status = ?3, closedDate = ?4 where id = ?5", closedEventModel.getWinner(),
                        closedEventModel.getClosedBy(),EventStatus.CLOSED.toString(),closedEventModel.getClosedDate(),closedEventModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "pingpong":
                if (Optional.ofNullable(PingPongEvent.findById(closedEventModel.getEventId())).isPresent()) {
                    PingPongEvent.update("winner = ?1, closedBy = ?2, status = ?3, closedDate = ?4 where id = ?5", closedEventModel.getWinner(),
                        closedEventModel.getClosedBy(),EventStatus.CLOSED.toString(),closedEventModel.getClosedDate(), closedEventModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    @Transactional
    public boolean addPlayer(UpdatePlayersModel updatePlayersModel) {
        switch (updatePlayersModel.getType()) {
            case "chess":
                Optional<ChessEvent> optionalChessEvent = Optional.ofNullable(ChessEvent.findById(updatePlayersModel.getEventId()));
                if (optionalChessEvent.isPresent()) {
                    ChessEvent chessEvent = optionalChessEvent.get();
                    if(isMaxPlayersReached(chessEvent, EventFactory.CHESS_EVENT_MAX_PLAYERS)){
                        logger.info("Max players reached for event id {}, can't add another player", updatePlayersModel.getEventId());
                        return false;
                    } 
                    // if(Arrays.stream(chessEvent.getPlayers()).anyMatch(updatePlayersModel.getPlayerName()::equals)){
                    //     logger.info("Player {} already exists for event id {}", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
                    //     return false;
                    // }
                    ChessEvent.update("players = ?1 where id = ?2", addPlayer(chessEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    logger.info("Added player {}, event id: {} ", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "swimming":
                Optional<SwimmingEvent> optionalSwimmingEvent = Optional.ofNullable(SwimmingEvent.findById(updatePlayersModel.getEventId()));
                if (optionalSwimmingEvent.isPresent()) {
                    SwimmingEvent swimmingEvent = optionalSwimmingEvent.get();
                    if(isMaxPlayersReached(swimmingEvent, EventFactory.SWIMMING_EVENT_MAX_PLAYERS)) return false;
                    SwimmingEvent.update("players = ?1 where id = ?2", addPlayer(swimmingEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "tennis":
                Optional<TennisEvent> optionalTennisEvent = Optional.ofNullable(TennisEvent.findById(updatePlayersModel.getEventId()));
                if (optionalTennisEvent.isPresent()) {
                    TennisEvent tennisEvent = optionalTennisEvent.get();
                    if(isMaxPlayersReached(tennisEvent, EventFactory.TENNIS_EVENT_MAX_PLAYERS)) return false;
                    TennisEvent.update("players = ?1 where id = ?2", addPlayer(tennisEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "pingpong":
                Optional<PingPongEvent> optionalPingPongEvent = Optional.ofNullable(PingPongEvent.findById(updatePlayersModel.getEventId()));
                if (optionalPingPongEvent.isPresent()) {
                    PingPongEvent pingPongEvent = optionalPingPongEvent.get();
                    if(isMaxPlayersReached(pingPongEvent, EventFactory.PINGPONG_EVENT_MAX_PLAYERS)) return false;
                    PingPongEvent.update("players = ?1 where id = ?2", addPlayer(pingPongEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    @Transactional
    public boolean removePlayer(UpdatePlayersModel updatePlayersModel) {
        switch (updatePlayersModel.getType().toLowerCase()) {
            case "chess":
                Optional<ChessEvent> optionalChessEvent = Optional.ofNullable(ChessEvent.findById(updatePlayersModel.getEventId()));
                if (optionalChessEvent.isPresent()) {
                    ChessEvent chessEvent = optionalChessEvent.get();
                    ChessEvent.update("players = ?1 where id = ?2", removePlayer(chessEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    logger.info("Removed player {} from event with id {}", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "swimming":
                Optional<SwimmingEvent> optionalSwimmingEvent = Optional.ofNullable(SwimmingEvent.findById(updatePlayersModel.getEventId()));
                if (optionalSwimmingEvent.isPresent()) {
                    SwimmingEvent swimmingEvent = optionalSwimmingEvent.get();
                    SwimmingEvent.update("players = ?1 where id = ?2", removePlayer(swimmingEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            case "tennis":
                Optional<TennisEvent> optionalTennisEvent = Optional.ofNullable(TennisEvent.findById(updatePlayersModel.getEventId()));
                if (optionalTennisEvent.isPresent()) {
                    TennisEvent tennisEvent = optionalTennisEvent.get();
                        TennisEvent.update("players = ?1 where id = ?2", removePlayer(tennisEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                        return true;
                } else {
                    return false;
                }
            case "pingpong":
                Optional<PingPongEvent> optionalPingPongEvent = Optional.ofNullable(PingPongEvent.findById(updatePlayersModel.getEventId()));
                if (optionalPingPongEvent.isPresent()) {
                    PingPongEvent pingPongEvent = optionalPingPongEvent.get();
                    PingPongEvent.update("players = ?1 where id = ?2", removePlayer(pingPongEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
                    return true;
                } else {
                    return false;
                }
            default:
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
}
