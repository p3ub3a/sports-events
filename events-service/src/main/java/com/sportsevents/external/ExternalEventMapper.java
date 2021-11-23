package com.sportsevents.external;

import com.sportsevents.entity.Event;

public class ExternalEventMapper {
    public static Event mapExternalEventToEvent(ExternalEvent externalEvent){
        return new Event.EventBuilder()
        .id(externalEvent.getId())
        .name(externalEvent.getName())
        .type(externalEvent.getType())
        .status(externalEvent.getStatus())
        .scheduledDate(externalEvent.getScheduledDate())
        .duration(externalEvent.getDuration())
        .isOutdoors(externalEvent.isOutdoors())
        .closedDate(externalEvent.getClosedDate())
        .facilitator(externalEvent.getFacilitator())
        .closedBy(externalEvent.getClosedBy())
        .location(externalEvent.getLocation())
        .winner(externalEvent.getWinner())
        .players(externalEvent.getPlayers().toArray(String[]::new))
        .maxPlayers(externalEvent.getMaxPlayers())
        .build();
    }

    public static Event updateEvent(Event event, ExternalEvent externalEvent){
        event.setName(externalEvent.getName());
        event.setType(externalEvent.getType());
        event.setStatus(externalEvent.getStatus());
        event.setScheduledDate(externalEvent.getScheduledDate());
        event.setDuration(externalEvent.getDuration());
        event.setOutdoors(externalEvent.isOutdoors());
        event.setClosedDate(externalEvent.getClosedDate());
        event.setFacilitator(externalEvent.getFacilitator());
        event.setClosedBy(externalEvent.getClosedBy());
        event.setLocation(externalEvent.getLocation());
        event.setWinner(externalEvent.getWinner());
        event.setPlayers(externalEvent.getPlayers().toArray(String[]::new));
        event.setMaxPlayers(externalEvent.getMaxPlayers());

        return event;
    }
}
