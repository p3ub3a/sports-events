package com.sportsevents.eventsservicespring.utils;

import java.util.Optional;

import com.sportsevents.eventsservicespring.api.model.EventModel;
import com.sportsevents.eventsservicespring.entity.Event;
import com.sportsevents.eventsservicespring.entity.EventStatus;
import com.sportsevents.eventsservicespring.entity.EventType;
import com.sportsevents.eventsservicespring.entity.Event.EventBuilder;

public class EventsUtils {
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
            int duration;
            int maxPlayers;
            String type;
            switch(eventModel.getType()){
                case "chess":
                    duration = CHESS_EVENT_DURATION;
                    maxPlayers = CHESS_EVENT_MAX_PLAYERS;
                    type = EventType.CHESS.toString();
                    break;
                case "pingpong":
                    duration = PINGPONG_EVENT_DURATION;
                    maxPlayers = PINGPONG_EVENT_MAX_PLAYERS;
                    type = EventType.PINGPONG.toString();
                    break;
                case "swimming":
                    duration = SWIMMING_EVENT_DURATION;
                    maxPlayers = SWIMMING_EVENT_MAX_PLAYERS;
                    type = EventType.SWIMMING.toString();
                    break;
                case "tennis":
                    duration = TENNIS_EVENT_DURATION;
                    maxPlayers = TENNIS_EVENT_MAX_PLAYERS;
                    type = EventType.TENNIS.toString();
                    break;
                default:
                    return Optional.empty();
            }

            return Optional.of(new EventBuilder()
                .name(eventModel.getName())
                .duration(duration)
                .scheduledDate(eventModel.getScheduledDate())
                .isOutdoors(eventModel.isOutdoors())
                .location(eventModel.getLocation())
                .facilitator(eventModel.getFacilitator())
                .status(EventStatus.CREATED.toString())
                .type(type)
                .maxPlayers(maxPlayers)
                .build());
    }
    
    public static Optional<EventType> getType(String type){
        switch(type){
            case "chess":
                return Optional.of(EventType.CHESS);
            case "pingpong":
                return Optional.of(EventType.PINGPONG);
            case "swimming":
                return Optional.of(EventType.SWIMMING);
            case "tennis":
                return Optional.of(EventType.TENNIS);
            default:
                return Optional.empty();
        }
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
