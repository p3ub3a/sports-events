package com.sportsevents.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.sportsevents.api.model.UpdatePlayersModel;
import com.sportsevents.api.model.ClosedEventModel;
import com.sportsevents.api.model.EventModel;
import com.sportsevents.api.model.GetEventsResponse;
import com.sportsevents.api.model.LeaderboardEntryModel;
import com.sportsevents.api.model.PageRequest;
import com.sportsevents.entity.Event;
import com.sportsevents.entity.EventStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.security.identity.SecurityIdentity;

@ApplicationScoped
public class EventServiceImpl implements EventService {
    @Inject
    private SecurityIdentity identity;

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

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
            event.persist();
            logger.info("Persisted event with id {}, event description: {}", event.getId(), event.toString());
        }else{
            logger.info("Unable to persist event with id {}, past event date: {}", event.getId(), event.getScheduledDate().toString());
            throw new IllegalArgumentException("Unable to persist event, past event date");
        }
        
    }

    @Override
    public Optional<Event> getEvent(Long id) {
        return Optional.ofNullable(Event.findById(id));
    }

    @Override
    public GetEventsResponse getEvents(PageRequest pageRequest) {
        logger.info("Get events, type: {}, pageNum: {}, pageSize: {}",pageRequest.getType(), pageRequest.getPageNum(), pageRequest.getPageSize());

        GetEventsResponse response = new GetEventsResponse();
        response.setRecords(Collections.emptyList());

        if(pageRequest.getType().equalsIgnoreCase("home")){
            runHomeQuery(pageRequest, response);
        }

        LocalDateTime now = LocalDateTime.now();
        if(pageRequest.getType().equalsIgnoreCase("future")){
            runFutureQuery(pageRequest, response, now);
        }
        if(pageRequest.getType().equalsIgnoreCase("past")){
            runPastQuery(pageRequest, response, now);
        }

        return response;
    }

    private void runHomeQuery(PageRequest pageRequest, GetEventsResponse response) {
        String userName = identity.getPrincipal().getName();
        long[] totalRecords = {0L};
        
        List<Event> events = Event.findAll()
            .list()
            .stream()
            .map(event -> (Event) event)
            .filter(event -> {
                if(event.getPlayers() != null){
                    boolean isParticipating = Arrays.asList(event.getPlayers()).contains(userName);
                    if(isParticipating){
                        totalRecords[0] ++;
                        return isParticipating;
                    }
                }
                return  false;
            })
            .collect(Collectors.toList());

        int pagesNr = Double.valueOf(Math.ceil((double)totalRecords[0] / pageRequest.getPageSize())).intValue();
        int pageNum = pageRequest.getPageNum();
        response.setPagesNr(pagesNr);
        if(events.size() > 0){
            response.setRecords(events.subList(pageRequest.getPageNum() * pageRequest.getPageSize(), (int) Math.min(++pageNum * pageRequest.getPageSize(), events.size())));
        }   
    }

    private void runFutureQuery(PageRequest pageRequest, GetEventsResponse response, LocalDateTime now) {
        PanacheQuery<Event> panacheQuery = Event.find("?1 < scheduledDate", now).page(pageRequest.getPageNum(), pageRequest.getPageSize());
        response.setPagesNr(panacheQuery.pageCount());
        response.setRecords(panacheQuery
            .list()
            .stream()
            .map(event -> (Event) event)
            .collect(Collectors.toList()));
    }

    private void runPastQuery(PageRequest pageRequest, GetEventsResponse response, LocalDateTime now) {
        PanacheQuery<Event> panacheQuery = Event.find("?1 > scheduledDate", now).page(pageRequest.getPageNum(), pageRequest.getPageSize());
        response.setPagesNr(panacheQuery.pageCount());
        response.setRecords(panacheQuery
            .list()
            .stream()
            .map(event -> (Event) event)
            .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        try{
            logger.info("Deleting event with id {} ", id);
            Event.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public boolean closeEvent(ClosedEventModel closedEventModel) {
        Optional<Event> chessEventOpt = Optional.ofNullable(Event.findById(closedEventModel.getEventId()));
        if (chessEventOpt.isPresent()) {
            if(chessEventOpt.get().getStatus().equals(EventStatus.CLOSED.toString())){
                logger.info("Event with id {} is already closed, update not required", closedEventModel.getEventId());
                return false;
            }
            Event.update("winner = ?1, closedBy = ?2, status = ?3, closedDate = ?4 where id = ?5", closedEventModel.getWinner(),
                closedEventModel.getClosedBy(),EventStatus.CLOSED.toString(),closedEventModel.getClosedDate(),closedEventModel.getEventId());
            logger.info("Closed event with id {} ", closedEventModel.getEventId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addPlayer(UpdatePlayersModel updatePlayersModel) {
        Optional<Event> optionalChessEvent = Optional.ofNullable(Event.findById(updatePlayersModel.getEventId()));
        if (optionalChessEvent.isPresent()) {
            Event chessEvent = optionalChessEvent.get();
            if(isMaxPlayersReached(chessEvent, EventFactory.CHESS_EVENT_MAX_PLAYERS)){
                logger.info("Max players reached for event id {}, can't add another player", updatePlayersModel.getEventId());
                return false;
            } 
            // if(Arrays.stream(chessEvent.getPlayers()).anyMatch(updatePlayersModel.getPlayerName()::equals)){
            //     logger.info("Player {} already exists for event id {}", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
            //     return false;
            // }
            Event.update("players = ?1 where id = ?2", addPlayer(chessEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
            logger.info("Added player {}, event id: {} ", updatePlayersModel.getPlayerName(),updatePlayersModel.getEventId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removePlayer(UpdatePlayersModel updatePlayersModel) {
        Optional<Event> optionalChessEvent = Optional.ofNullable(Event.findById(updatePlayersModel.getEventId()));
        if (optionalChessEvent.isPresent()) {
            Event chessEvent = optionalChessEvent.get();
            Event.update("players = ?1 where id = ?2", removePlayer(chessEvent.getPlayers(), updatePlayersModel.getPlayerName()), updatePlayersModel.getEventId());
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
        // select e.winner, count(e.winner) as wins from event e where status = 'CLOSED' group by e.winner order by 2 desc limit 20
        List<Event> leaderboard = Event.find("select e.winner, count(e.winner) as wins from Event e where status = 'CLOSED' group by e.winner order by 2 desc").page(Page.ofSize(20)).firstPage().list();

        return mapLeaderboard(leaderboard);
    }

    @SuppressWarnings({ "unchecked"})
    private Optional<List<LeaderboardEntryModel>> mapLeaderboard(List<Event> leaderboard){
        List<LeaderboardEntryModel> leaderboardEntryList = new ArrayList<>();
        long rank = 1;
        for(Object[] row : (List<Object[]>)(Object) leaderboard ){
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
