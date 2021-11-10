package com.sportsevents.service;

import java.util.Optional;

import com.sportsevents.api.model.EventModel;
import com.sportsevents.entity.Event;
import com.sportsevents.entity.EventStatus;
import com.sportsevents.entity.EventType;
import com.sportsevents.entity.Event.EventBuilder;

public class EventFactory {

    public static final String CHESS_EVENT_NAME = "Chess match";
    public static final String PINGPONG_EVENT_NAME = "Pingpong game";
    public static final String SWIMMING_EVENT_NAME = "Swimming competition";
    public static final String TENNIS_EVENT_NAME = "Tennis game";

    public static final int CHESS_EVENT_DURATION = 30;
    public static final int PINGPONG_EVENT_DURATION = 15;
    public static final int SWIMMING_EVENT_DURATION = 30;
    public static final int TENNIS_EVENT_DURATION = 120;

    public static final int CHESS_EVENT_MAX_PLAYERS = 2;
    public static final int PINGPONG_EVENT_MAX_PLAYERS = 4;
    public static final int SWIMMING_EVENT_MAX_PLAYERS = 8;
    public static final int TENNIS_EVENT_MAX_PLAYERS = 4;

    public static Optional<Event> getSportsEvent(EventModel eventModel){
        if(eventModel.getType().equalsIgnoreCase(EventType.CHESS.toString())){
            return Optional.of(new EventBuilder()
                .name(CHESS_EVENT_NAME)
                .duration(CHESS_EVENT_DURATION)
                .scheduledDate(eventModel.getScheduledDate())
                .isOutdoors(eventModel.isOutdoors())
                .location(eventModel.getLocation())
                .facilitator(eventModel.getFacilitator())
                .status(EventStatus.CREATED.toString())
                .type(EventType.CHESS.toString())
                .maxPlayers(CHESS_EVENT_MAX_PLAYERS)
                .build());
        }

        if(eventModel.getType().equalsIgnoreCase(EventType.PINGPONG.toString())){
            return Optional.of(new EventBuilder()
                .name(PINGPONG_EVENT_NAME)
                .duration(PINGPONG_EVENT_DURATION)
                .scheduledDate(eventModel.getScheduledDate())
                .isOutdoors(eventModel.isOutdoors())
                .location(eventModel.getLocation())
                .facilitator(eventModel.getFacilitator())
                .status(EventStatus.CREATED.toString())
                .type(EventType.PINGPONG.toString())
                .maxPlayers(PINGPONG_EVENT_MAX_PLAYERS)
                .build());
        }

        if(eventModel.getType().equalsIgnoreCase(EventType.SWIMMING.toString())){
            return Optional.of(new EventBuilder()
                .name(SWIMMING_EVENT_NAME)
                .duration(SWIMMING_EVENT_DURATION)
                .scheduledDate(eventModel.getScheduledDate())
                .isOutdoors(eventModel.isOutdoors())
                .location(eventModel.getLocation())
                .facilitator(eventModel.getFacilitator())
                .status(EventStatus.CREATED.toString())
                .type(EventType.SWIMMING.toString())
                .maxPlayers(SWIMMING_EVENT_MAX_PLAYERS)
                .build());
        }

        if(eventModel.getType().equalsIgnoreCase(EventType.TENNIS.toString())){
            return Optional.of(new EventBuilder()
                .name(TENNIS_EVENT_NAME)
                .duration(TENNIS_EVENT_DURATION)
                .scheduledDate(eventModel.getScheduledDate())
                .isOutdoors(eventModel.isOutdoors())
                .location(eventModel.getLocation())
                .facilitator(eventModel.getFacilitator())
                .status(EventStatus.CREATED.toString())
                .type(EventType.TENNIS.toString())
                .maxPlayers(TENNIS_EVENT_MAX_PLAYERS)
                .build());
        }

        return Optional.empty();
        
    }

    public static Optional<Integer> getMaxPlayers(String type){
        switch(type.toLowerCase()){
            case "chess":
                return Optional.of(CHESS_EVENT_MAX_PLAYERS);
            case "pingpong":
                return Optional.of(PINGPONG_EVENT_MAX_PLAYERS);
            case "swimming":
                return Optional.of(SWIMMING_EVENT_MAX_PLAYERS);
            case "tennis":
                return Optional.of(TENNIS_EVENT_MAX_PLAYERS);
            default:
                return Optional.empty();
        }
    }

}
